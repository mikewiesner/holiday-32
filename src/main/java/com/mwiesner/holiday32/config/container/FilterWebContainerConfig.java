package com.mwiesner.holiday32.config.container;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;

public class FilterWebContainerConfig implements WebApplicationInitializer {

	public void onStartup(ServletContext sc) throws ServletException {
		// Register additional Filters
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		sc.addFilter("CharacterEncodingFilter", encodingFilter)
				.addMappingForUrlPatterns(null, false, "/*");
		sc.addFilter("HttpMethodFilter", new HiddenHttpMethodFilter())
				.addMappingForUrlPatterns(null, false, "/*");
		sc.addFilter("OpenEntityManagerInViewFilter",
				new OpenEntityManagerInViewFilter()).addMappingForUrlPatterns(
				null, false, "/*");

	}

}
