package com.calmeter.core.food.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.IFood;
import com.calmeter.core.food.model.Meal;
import com.calmeter.core.food.repositroy.IFoodItemRepository;
import com.calmeter.core.food.repositroy.IMealRepository;

@RestController
@RequestMapping("/api/food-item")
public class FoodItemApiController {

	@Autowired
	IFoodItemRepository foodItemRepository;

	@Autowired
	IMealRepository mealRepository;

	@RequestMapping(value = "/allFoodItems", method = RequestMethod.GET)
	ResponseEntity<Collection<FoodItem>> getAllFoodItems () {
		List<FoodItem> foodItems = foodItemRepository.findAll ();
		if (foodItems.size () < 1) {
			return new ResponseEntity<Collection<FoodItem>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Collection<FoodItem>> (foodItems, HttpStatus.OK);
	}

	@RequestMapping(value = "/allMeals", method = RequestMethod.GET)
	ResponseEntity<Collection<Meal>> getAllMeals () {
		List<Meal> meals = mealRepository.findAll ();
		if (meals.size () < 1) {
			return new ResponseEntity<Collection<Meal>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Collection<Meal>> (meals, HttpStatus.OK);
	}

	@RequestMapping(value = "/allFood", method = RequestMethod.GET)
	ResponseEntity<Collection<IFood>> getAll () {
		List<FoodItem> foodItems = foodItemRepository.findAll ();
		List<Meal> meals = mealRepository.findAll ();
		List<IFood> allFood = new ArrayList<IFood> ();
		allFood.addAll (foodItems);
		allFood.addAll (meals);
		if (allFood.size () < 1) {
			return new ResponseEntity<Collection<IFood>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Collection<IFood>> (allFood, HttpStatus.OK);
	}

	@RequestMapping(value = "/createFoodItem", method = RequestMethod.POST)
	ResponseEntity<?> createFoodItem (@RequestBody FoodItem foodItem) {

		logger.info ("Creating food : {}", foodItem);
		FoodItem createdFoodItem = foodItemRepository.save (foodItem);
		logger.info ("Food item created id: {}", createdFoodItem.getId ());
		return new ResponseEntity<String> (HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateFoodItem", method = RequestMethod.PUT)
	ResponseEntity<?> updateFoodItem(@PathVariable( "id" ) Long id, @RequestBody FoodItem foodItem) {		
		logger.info("Updating food item: {}", id);	
		foodItem = foodItemRepository.save (foodItem);
		logger.info ("Food item updated id: {}", foodItem.getId ());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteFoodItem/{id}", method = RequestMethod.DELETE)
	ResponseEntity<?> deleteFoodItem (@PathVariable("id") Long id) {
		logger.info ("Deleting food item: {}", id);
		foodItemRepository.delete (id);
		return new ResponseEntity<String> (HttpStatus.OK);
	}

	public static final Logger logger = LoggerFactory.getLogger (FoodItemApiController.class);

}
