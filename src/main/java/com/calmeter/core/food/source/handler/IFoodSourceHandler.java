package com.calmeter.core.food.source.handler;

import java.util.List;

import com.calmeter.core.food.model.FoodItem;

public interface IFoodSourceHandler {

	public FoodItem getItemFromID (Long id);

	public List<FoodItem> search (String search);

}
