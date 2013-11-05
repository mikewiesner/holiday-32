	package com.mwiesner.holiday32.config.container;


import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.mwiesner.holiday32.config.core.SpringCoreConfig;
import com.mwiesner.holiday32.config.web.SimpleSpringWebMvcConfig;


/**
 * Replacement for web.xml
 * 
 * @author MWiesner
 *
 */
public class WebContainerConfig extends AbstractAnnotationConfigDispatcherServletInitializer {



	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SpringCoreConfig.class};
	}


	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {SimpleSpringWebMvcConfig.class};
	}


	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	protected WebApplicationContext createRootApplicationContext() {
		ConfigurableWebApplicationContext context = (ConfigurableWebApplicationContext) super.createRootApplicationContext();
		String activeProfile = "test";
		context.getEnvironment().addActiveProfile(activeProfile);
		return context;
	}
	
	

}
