package com.calmeter.core.spring;

import org.springframework.context.ApplicationContext;

public class ApplicationContextProvider {

	
	private static ApplicationContext appilicationContext;

	public static ApplicationContext getAppilicationContext() {
		return appilicationContext;
	}

	public static void setAppilicationContext(ApplicationContext appilicationContext) {
		ApplicationContextProvider.appilicationContext = appilicationContext;
	}
	
}
