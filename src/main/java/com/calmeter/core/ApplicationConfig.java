package com.calmeter.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class ApplicationConfig {

	@Bean
	public InternalResourceViewResolver viewResolver () {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver ();
		viewResolver.setViewClass (JstlView.class);
		viewResolver.setPrefix ("/");
		viewResolver.setSuffix (".jsp");
		return viewResolver;
	}

}
