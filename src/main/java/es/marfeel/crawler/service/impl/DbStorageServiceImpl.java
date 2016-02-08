package es.marfeel.crawler.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.marfeel.crawler.entity.Website;
import es.marfeel.crawler.repository.WebsiteRepository;
import es.marfeel.crawler.service.ResultStorage;

@Component
public class DbStorageServiceImpl implements ResultStorage {
	
	@Autowired
	private WebsiteRepository repository;
	
	@Override
	public void save(List<Website> websites) {
		repository.save(websites);
	}

}
