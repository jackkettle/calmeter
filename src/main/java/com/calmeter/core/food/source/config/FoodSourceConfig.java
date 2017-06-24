package com.calmeter.core.food.source.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(locations = "classpath:/food-source.properties", prefix="food.source")
public class FoodSourceConfig {

	private String tescoSubscriptionKey;

	
	public String getTescoSubscriptionKey () {
		return tescoSubscriptionKey;
	}

	public void setTescoSubscriptionKey (String tescoSubscriptionKey) {
		this.tescoSubscriptionKey = tescoSubscriptionKey;
	}

}
