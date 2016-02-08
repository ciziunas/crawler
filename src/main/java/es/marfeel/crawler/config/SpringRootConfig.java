package es.marfeel.crawler.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import es.marfeel.crawler.service.Marfeelizable;
import es.marfeel.crawler.service.impl.MarfeelizableServiceImpl;
import es.marfeel.crawler.validator.JsonValidator;
import es.marfeel.crawler.validator.JsonValidatorByRegexImpl;

/**
 * Enable to scan the service classes
 * 
 * @author Marius
 *
 */
@Configuration
@ComponentScan({ "es.marfeel.crawler", "es.marfeel.crawler.service", "es.marfeel.crawler.validator" })
@EnableTransactionManagement
@EnableJpaRepositories({ "es.marfeel.crawler.repository" })
@PropertySource("classpath:application.properties")
public class SpringRootConfig {

	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM_TO_DDL = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_CREATE = "create";
	private static final String PROPERTY_NAME_HIBERNATE_MYSQL_DIALECT = "org.hibernate.dialect.MySQL5Dialect";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "es.marfeel.crawler.entity";

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);
		entityManagerFactoryBean.setJpaProperties(hibProperties());
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public JsonValidator getJsonValidator() {
		return new JsonValidatorByRegexImpl();
	}

	@Bean
	public Marfeelizable getMarfeelizable() {
		return new MarfeelizableServiceImpl();
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, PROPERTY_NAME_HIBERNATE_MYSQL_DIALECT);
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_HBM_TO_DDL, PROPERTY_NAME_HIBERNATE_CREATE);
		return properties;
	}

}
