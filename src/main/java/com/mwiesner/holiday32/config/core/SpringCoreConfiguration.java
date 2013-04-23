package com.mwiesner.holiday32.config.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@Import({ConfigInfraProduction.class,ConfigInfraTest.class})
@EnableJpaRepositories("com.mwiesner.holiday32.repo")
@ComponentScan(basePackages={"com.mwiesner.holiday32.repo","com.mwiesner.holiday32.service"})
public class SpringCoreConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	
	@Bean
	public JpaTransactionManager transactionManager() throws IOException {
		return new JpaTransactionManager(entityManager());
	}
	

	@Bean
	public EntityManagerFactory entityManager() throws IOException {
		return entityManagerFactory().getObject();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IOException {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setPersistenceUnitName("persistenceUnit");
		emf.setDataSource(dataSource);
		emf.setPackagesToScan("com.mwiesner.holiday32.domain");
		emf.setPersistenceProviderClass(HibernatePersistence.class);
		emf.setJpaProperties(jpaProps());
		emf.afterPropertiesSet();
		return emf;
	}
	
	public Properties jpaProps() throws IOException {
		InputStream inputStream = resourceLoader.getResource("classpath:META-INF/spring/jpa.properties").getInputStream();
		Properties properties = new Properties();
		properties.load(inputStream);
		return properties;
	}
	
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
	

}
