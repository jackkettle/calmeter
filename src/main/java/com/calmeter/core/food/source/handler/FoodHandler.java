package com.calmeter.core.food.source.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.repositroy.IFoodItemRepository;

@Component
public class FoodHandler
		implements IFoodSourceHandler {

	@Autowired
	IFoodItemRepository foodItemRepository;

	@Override
	public FoodItem getItemFromID (Long id) {
		return foodItemRepository.findOne (id);
	}

}
