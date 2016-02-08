package es.marfeel.crawler.action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import es.marfeel.crawler.entity.Website;
import es.marfeel.crawler.service.Marfeelizable;

public class WebsitesVisitingAction extends RecursiveTask<List<Website>> {

	private static final long serialVersionUID = 6483190184918015082L;
	private final int threshold;
	private String[] websites;
	private int start;
	private int end;
	private Marfeelizable marfeelizableService;

	public WebsitesVisitingAction(String[] websites, int start, int end, int threshold,
			Marfeelizable marfeelizableService) {
		this.threshold = threshold;
		this.websites = websites;
		this.start = start;
		this.end = end;
		this.marfeelizableService = marfeelizableService;
	}

	@Override
	protected List<Website> compute() {
		if (end - start <= threshold) {
			List<Website> sites = new ArrayList<Website>();
			for (int i = start; i < end; i++) {
				Website website = marfeelizableService.marfeelize(websites[i]);
				if (website != null) {
					sites.add(website);
				}
			}
			return sites;
		} else { // divide the task
			int halfWay = ((end - start) / 2) + start;
			try {
				WebsitesVisitingAction a1 = new WebsitesVisitingAction(websites, start, halfWay, threshold,
						marfeelizableService.getClass().newInstance());
				a1.fork(); // queue left half of task
				WebsitesVisitingAction a2 = new WebsitesVisitingAction(websites, halfWay, end, threshold,
						marfeelizableService.getClass().newInstance());
				List<Website> sites2 = a2.compute(); // work on right half of
														// task
				List<Website> sites1 = a1.join(); // wait for queued task to be
													// complete
				sites1.addAll(sites2);
				return sites1;
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}
	}

}
