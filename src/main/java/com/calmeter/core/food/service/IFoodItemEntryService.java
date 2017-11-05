package com.calmeter.core.food.service;

import java.util.Optional;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.model.Meal;

public interface IFoodItemEntryService {

	Optional<FoodItemEntry> getFoodItemEntry(Long id);

	FoodItemEntry save(FoodItemEntry foodItemEntry);
	
	boolean isFoodItemUsed(FoodItem foodItem);
	
	boolean isMealUsed(Meal foodItem);

}
