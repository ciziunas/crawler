package es.marfeel.crawler.controller;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.marfeel.crawler.action.DbStoringAction;
import es.marfeel.crawler.action.WebsitesVisitingAction;
import es.marfeel.crawler.entity.Website;
import es.marfeel.crawler.service.Marshaller;
import es.marfeel.crawler.service.ResultStorage;
import es.marfeel.crawler.service.impl.MarfeelizableServiceImpl;
import es.marfeel.crawler.validator.JsonValidator;

@Controller
public class CrawlerController {
	
	private static final int TASKS_PARALELISM_LEVEL = 128;
	private static final int TASKS_EXECUTION_THRESHOLD = 20;

	@Autowired
	private ResultStorage dbStorageService;
	@Autowired
	private JsonValidator jsonValidator;
	@Autowired
	private Marshaller jsonMarshaller;

	/**
	 * Service for visiting requested websites. if valid json data is provided, status is returned as a response.
	 * Results are saved through dbStorageService asyncronously.
	 * @param json websites url list
	 * @return
	 */
	@RequestMapping(value = "/check-sites", method = RequestMethod.POST)
	public ResponseEntity<String> checkSites(@RequestBody String json) {
		if (!jsonValidator.isValid(json)) {
			return ResponseEntity.badRequest().body("The request sent by the client was syntactically incorrect.");
		}
		System.out.println("JSON is valid, unmarshaling to Java.");
		String[] websites = jsonMarshaller.unMarshal(json);
		System.out.println("Objects created. Visiting sites concurrently started");
		List<Website> marfeelizedSites = executeConcurrentVisiting(websites);
		System.out.println("Visiting sites finished. Saving results to DB asynchronously");
		
		storeResults(marfeelizedSites);

		return ResponseEntity.ok("Created");
	}
	
	private void storeResults(List<Website> marfeelizedSites) {
		DbStoringAction dbStoringAction = new DbStoringAction(dbStorageService, marfeelizedSites);
		new Thread(dbStoringAction).start();
	}
	
	private List<Website> executeConcurrentVisiting(String[] websites) {
		ForkJoinPool fjPool = new ForkJoinPool(TASKS_PARALELISM_LEVEL);
		int threshold = websites.length / TASKS_EXECUTION_THRESHOLD;
		WebsitesVisitingAction wvAction = new WebsitesVisitingAction(websites, 0, websites.length, threshold, new MarfeelizableServiceImpl());
		return fjPool.invoke(wvAction);
	}

}
