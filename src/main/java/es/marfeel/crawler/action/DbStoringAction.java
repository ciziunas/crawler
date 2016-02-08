package es.marfeel.crawler.action;

import java.util.List;

import es.marfeel.crawler.entity.Website;
import es.marfeel.crawler.service.ResultStorage;

public class DbStoringAction implements Runnable {
	
	private ResultStorage resultStorage;
	private List<Website> websites;
	
	public DbStoringAction(ResultStorage resultStorage, List<Website> websites) {
		this.resultStorage = resultStorage;
		this.websites = websites;
	}

	@Override
	public void run() {
		resultStorage.save(websites);
	}
	
}