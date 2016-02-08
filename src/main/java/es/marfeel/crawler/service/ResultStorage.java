package es.marfeel.crawler.service;

import java.util.List;

import es.marfeel.crawler.entity.Website;

public interface ResultStorage {
	
	public void save(List<Website> websites);

}
