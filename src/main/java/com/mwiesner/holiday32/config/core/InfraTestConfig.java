package com.mwiesner.holiday32.config.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;

@Configuration
@Profile("test")
public class InfraTestConfig {

	
	@Bean
	public EmbeddedDatabaseFactoryBean dataSource() {
		// for testing we just use an in memory db
		return new EmbeddedDatabaseFactoryBean();
	}
}
