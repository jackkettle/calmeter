package com.calmeter.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.calmeter.core.spring.ApplicationContextProvider;


public class Main {
	
	public static void main(String[] args) {
			
		logger.info("Starting application...");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-configuration.xml");
		ApplicationContextProvider.setAppilicationContext(context);
	
		
		
	}

	private static Logger logger = LoggerFactory.getLogger(Main.class);
}
