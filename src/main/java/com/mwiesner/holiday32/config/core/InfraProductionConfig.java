package com.mwiesner.holiday32.config.core;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value="classpath:META-INF/spring/database.properties")
@Profile("production")
public class InfraProductionConfig {
	
	@Autowired
	private Environment env;

	
	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		// a real datasource for production
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(env.getProperty("database.driverClassName"));
		ds.setUrl(env.getProperty("database.url"));
		ds.setUsername(env.getProperty("database.username"));
		ds.setPassword(env.getProperty("database.password"));
		ds.setTestOnBorrow(true);
		ds.setTestOnReturn(true);
		ds.setTestWhileIdle(true);
		ds.setTimeBetweenEvictionRunsMillis(1800000);
		ds.setNumTestsPerEvictionRun(3);
		ds.setMinEvictableIdleTimeMillis(1800000);
		return ds;
	}
}
