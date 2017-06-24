package com.calmeter.core.food.source.handler;

import java.util.List;

import com.calmeter.core.food.model.FoodItem;

public interface IFoodSourceHandler {

	FoodItem getItemFromID(Long id);

	List<FoodItem> search (String search);
	
}
