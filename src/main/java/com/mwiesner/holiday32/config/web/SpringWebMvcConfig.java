package com.mwiesner.holiday32.config.web;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesView;

import com.mwiesner.holiday32.web.HolidayWebConverters;

@Configuration
@ComponentScan(basePackages="com.mwiesner.holiday32.web")
public class SpringWebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	private HolidayWebConverters converters;

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
		super.configureDefaultServletHandling(configurer);
	}

	
	@Override
	public FormattingConversionService mvcConversionService() {
		FormattingConversionService registry = super.mvcConversionService();
		registry.addConverter(converters.getEmployeeToStringConverter());
		registry.addConverter(converters.getHolidayRequestToStringConverter());
		registry.addConverter(converters.getIdToEmployeeConverter());
		registry.addConverter(converters.getIdToHolidayRequestConverter());
		registry.addConverter(converters.getStringToEmployeeConverter(registry));
		registry.addConverter(converters.getStringToHolidayRequestConverter(registry));
		return registry;
		
	}

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		ResourceHandlerRegistration handler = registry.addResourceHandler("/resources/**");
		handler.addResourceLocations("/","classpath:/META-INF/web-resources/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/uncaughtException");
		registry.addViewController("/resourceNotFound");
		registry.addViewController("/dataAccessFailure");
	}
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		ms.setBasenames("WEB-INF/i18n/messages","WEB-INF/i18n/application");
		ms.setFallbackToSystemLocale(false);
		return ms;
	}
	
	@Bean
	public ResourceBundleThemeSource themeSource() {
		return new ResourceBundleThemeSource();
	}
	
	@Bean
	public CookieThemeResolver themeResolver() {
		CookieThemeResolver ctr = new CookieThemeResolver();
		ctr.setCookieName("theme");
		ctr.setDefaultThemeName("standard");
		return ctr;
	}
	
	@Bean
	public UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setViewClass(TilesView.class);
		return resolver;
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions(new String[] {"/WEB-INF/layouts/layouts.xml","/WEB-INF/views/**/views.xml"});
		return configurer;
	}
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(themeChangeInterceptor());
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	@Bean
	public ThemeChangeInterceptor themeChangeInterceptor() {
		return new ThemeChangeInterceptor();
	}
	
	public CookieLocaleResolver localeResolver () {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setCookieName("locale");
		return resolver;
	}
	
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}
	
	@Override
	protected void configureHandlerExceptionResolvers(
			List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(exceptionResolver());
	}
	
	public SimpleMappingExceptionResolver exceptionResolver() {
		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		resolver.setDefaultErrorView("uncaughtException");
		Properties mappings = new Properties();
		mappings.put(".DataAccessException", "dataAccessFailure");
		mappings.put(".NoSuchRequestHandlingMethodException", "resourceNotFound");
		mappings.put(".TypeMismatchException", "resourceNotFound");
		mappings.put(".MissingServletRequestParameterException", "resourceNotFound");
		resolver.setExceptionMappings(mappings);
		return resolver;
	}
	
	
	
	
}
