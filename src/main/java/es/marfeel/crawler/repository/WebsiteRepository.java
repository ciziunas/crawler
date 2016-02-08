package es.marfeel.crawler.repository;

import org.springframework.data.repository.CrudRepository;

import es.marfeel.crawler.entity.Website;

public interface WebsiteRepository extends CrudRepository<Website, Long> {

}
