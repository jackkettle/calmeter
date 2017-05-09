package com.calmeter.core.food.source.handler;

import com.calmeter.core.food.model.FoodItem;

public interface IFoodSourceHandler {

	FoodItem getItemFromID(Long id);
	
}
