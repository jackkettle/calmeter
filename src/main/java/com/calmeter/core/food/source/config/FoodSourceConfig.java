package com.calmeter.core.food.source.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:food-source.properties")
@ConfigurationProperties(prefix = "food.source")
public class FoodSourceConfig {

    private String tescoSubscriptionKey;

    public String getTescoSubscriptionKey() {
        return tescoSubscriptionKey;
    }

    public void setTescoSubscriptionKey(String tescoSubscriptionKey) {
        this.tescoSubscriptionKey = tescoSubscriptionKey;
    }

}
