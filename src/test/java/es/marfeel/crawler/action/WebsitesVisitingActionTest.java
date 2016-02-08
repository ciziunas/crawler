package es.marfeel.crawler.action;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import es.marfeel.crawler.entity.Website;
import es.marfeel.crawler.service.impl.MarfeelizableServiceImpl;

public class WebsitesVisitingActionTest {

	private static final String WEBSITE_URL1 = "http://www.google.lt";
	private static final String WEBSITE_URL2 = "http://www.linkedin.com";
	
	private WebsitesVisitingAction testee;
	
	@Before
	public void setUp() {
		//testee = new WebsitesVisitingAction();
	}
	
	@Test
	public void testValid() throws IOException {
		String[] inputWebsites = {WEBSITE_URL1, WEBSITE_URL2};
		MarfeelizableServiceImpl marfeelizableService = new MarfeelizableServiceImpl();
		MarfeelizableServiceImpl marfeelizableServiceSpy = Mockito.spy(marfeelizableService);
		Document document = new Document(WEBSITE_URL1);
		document.html("<head><title>news</title></head>");
		Mockito.doReturn(document).when(marfeelizableServiceSpy).getDocument(WEBSITE_URL1);
		Mockito.doReturn(document).when(marfeelizableServiceSpy).getDocument(WEBSITE_URL2);
		
		testee = new WebsitesVisitingAction(inputWebsites, 0, inputWebsites.length, 1, marfeelizableServiceSpy);
		WebsitesVisitingAction testeeSpy = Mockito.spy(testee);
		
		List<Website> websites = testee.compute();
//		ForkJoinPool fjPool = new ForkJoinPool(10);
//		List<Website> websites = fjPool.invoke(testeeSpy);
		System.out.println(websites);
	}
	
	
	
	
}
