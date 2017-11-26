package com.calmeter.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.calmeter.core.spring.ApplicationContextProvider;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		logger.info("Starting application...");
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		ApplicationContextProvider.resetApplicationContext (context);
	}
	
	private static Logger logger = LoggerFactory.getLogger(Main.class);
}
