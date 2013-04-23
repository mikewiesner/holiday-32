	package com.mwiesner.holiday32.config.container;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.mwiesner.holiday32.config.core.SpringCoreConfiguration;
import com.mwiesner.holiday32.config.web.SimpleSpringWebMvcConfig;
import com.mwiesner.holiday32.config.web.SpringWebMvcConfig;


/**
 * Replacement for web.xml
 * 
 * @author MWiesner
 *
 */
public class WebContainerConfig implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext sc)
			throws ServletException {
		String activeProfile = "test";
		// Register root Application Context
		AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
		root.setServletContext(sc);
		root.register(SpringCoreConfiguration.class);
		root.getEnvironment().addActiveProfile(activeProfile);
		// We don't startup the Application Context right now, instead we register a ServletContextListener.
		// The Listener will startup the Application Context later and also register it in the ServletContext.
		// This is needed that the DispatcherServlet can lookup that root context.
		sc.addListener(new ContextLoaderListener(root));

		// Register Dispatcher Servlet
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(SimpleSpringWebMvcConfig.class);
		dispatcherContext.setServletContext(sc);
		ServletRegistration.Dynamic servlet = sc.addServlet("holiday32", new DispatcherServlet(dispatcherContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
		// Register additional Filters
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		sc.addFilter("CharacterEncodingFilter", encodingFilter).addMappingForUrlPatterns(null, false, "/*");
		sc.addFilter("HttpMethodFilter", new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, false, "/*");
		sc.addFilter("OpenEntityManagerInViewFilter", new OpenEntityManagerInViewFilter()).addMappingForUrlPatterns(null, false, "/*");
	

	}

}
