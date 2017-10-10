package com.calmeter.core.food.source.handler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.service.IFoodItemService;

@Component
public class FoodHandler
		implements IFoodSourceHandler {

	@Autowired
	IFoodItemService foodItemService;

	@Override
	public Optional<FoodItem> getItemFromID (Long id) {
		return foodItemService.get (id);
	}

	@Override
	public List<FoodItem> search (String search) {
		return foodItemService.search (search);
	}

}
