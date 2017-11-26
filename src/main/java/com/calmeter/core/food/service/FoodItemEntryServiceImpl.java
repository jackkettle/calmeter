package com.calmeter.core.food.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.model.Meal;
import com.calmeter.core.food.repositroy.IFoodItemEntryRepository;

@Component("foodItemEntryService")
@Transactional
public class FoodItemEntryServiceImpl implements IFoodItemEntryService {

	@Autowired
	IFoodItemEntryRepository foodItemEntryRepository;
	
	@Override
	public Optional<FoodItemEntry> getFoodItemEntry(Long id){
		return foodItemEntryRepository.getById(id);
	}
	
	@Override
	public FoodItemEntry save(FoodItemEntry foodItemEntry){
		return foodItemEntryRepository.save(foodItemEntry);
	}

	@Override
	public boolean isFoodItemUsed(FoodItem foodItem) {
		return foodItemEntryRepository.getByFoodItemReference(foodItem).size() > 0;
	}

	@Override
	public boolean isMealUsed(Meal meal) {
		return foodItemEntryRepository.getByMealReference(meal).size() > 0;
	}
	
}
