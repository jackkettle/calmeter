package com.calmeter.core.food.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.IFood;
import com.calmeter.core.food.model.Meal;
import com.calmeter.core.food.repositroy.IFoodItemRepository;
import com.calmeter.core.food.repositroy.IMealRepository;

@RestController
@RequestMapping("/api/food")
public class FoodController {

	@Autowired
	IFoodItemRepository foodItemRepository;
	
	@Autowired
	IMealRepository mealRepository;
	
	@RequestMapping(value = "/allFoodItems", method = RequestMethod.GET)
	Collection<FoodItem> getAllFoodItems() {
		return foodItemRepository.findAll();
	}
	
	@RequestMapping(value = "/allMeals", method = RequestMethod.GET)
	Collection<Meal> getAllMeals() {
		return mealRepository.findAll();
	}
	
	@RequestMapping(value = "/allFood", method = RequestMethod.GET)
	Collection<IFood> getAll() {
		List<FoodItem> foodItems = foodItemRepository.findAll();
		List<Meal> meals = mealRepository.findAll();
		List<IFood> allFood = new ArrayList<IFood>();
		allFood.addAll(foodItems);
		allFood.addAll(meals);
		return allFood;

	}
	
}
