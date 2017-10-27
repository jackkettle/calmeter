package com.calmeter.core.food.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.repositroy.IFoodItemEntryRepository;

@Component("foodItemEntryService")
@Transactional
public class FoodItemEntryServiceImpl implements IFoodItemEntryService {

	@Autowired
	IFoodItemEntryRepository foodItemEntryRepository;
	
	public Optional<FoodItemEntry> getFoodItemEntry(Long id){
		return foodItemEntryRepository.getById(id);
	}
	
	public FoodItemEntry save(FoodItemEntry foodItemEntry){
		return foodItemEntryRepository.save(foodItemEntry);
	}
	
}