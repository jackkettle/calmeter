package com.calmeter.core.food.source.utils;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.calmeter.core.food.source.handler.IFoodSourceHandler;
import com.calmeter.core.food.source.model.FoodSource;
import com.calmeter.core.spring.ApplicationContextProvider;

@Component
public class FoodSourceHelper {

	public IFoodSourceHandler getFoodSourceHandler (FoodSource foodSource) {
		IFoodSourceHandler foodSourceHandler = null;
		try {
			foodSourceHandler = foodSource.getSourceHandler ().newInstance ();
			AutowireCapableBeanFactory factory = ApplicationContextProvider.getApplicationContext ().getAutowireCapableBeanFactory ();

			factory.autowireBean (foodSourceHandler);
			factory.initializeBean (foodSourceHandler, "foodSourceHandler");

		}
		catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace ();
		}

		return foodSourceHandler;
	}
	
}
