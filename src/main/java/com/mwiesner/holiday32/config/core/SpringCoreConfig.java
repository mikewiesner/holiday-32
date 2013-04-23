package com.mwiesner.holiday32.config.core;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SpringRepoConfig.class)
@ComponentScan(basePackages={"com.mwiesner.holiday32.repo","com.mwiesner.holiday32.service"})
public class SpringCoreConfig {
	

	
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
	

}
