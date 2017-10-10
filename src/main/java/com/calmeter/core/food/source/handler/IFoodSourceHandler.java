package com.calmeter.core.food.source.handler;

import java.util.List;
import java.util.Optional;

import com.calmeter.core.food.model.FoodItem;

public interface IFoodSourceHandler {

	public Optional<FoodItem> getItemFromID (Long id);

	public List<FoodItem> search (String search);

}
