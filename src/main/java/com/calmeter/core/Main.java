package com.calmeter.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
public class Main {
	
	public static void main(String[] args) {
			
		logger.info("Starting application...");
		
        SpringApplication.run(Main.class, args);
		
	}

	private static Logger logger = LoggerFactory.getLogger(Main.class);
}
