package com.calmeter.core.food.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.utils.UserHelper;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.service.IFoodItemService;
import com.calmeter.core.food.source.handler.IFoodSourceHandler;
import com.calmeter.core.food.source.model.FoodSource;
import com.calmeter.core.food.source.service.IFoodSourceService;
import com.calmeter.core.food.source.utils.FoodSourceHelper;

@RestController
@RequestMapping("/api/food-item")
public class FoodItemApiController {

	@Autowired
	UserHelper userHelper;

	@Autowired
	IFoodItemService foodItemService;

	@Autowired
	IFoodSourceService foodSourceService;

	@Autowired
	FoodSourceHelper foodSourceHelper;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<FoodItem> getFoodItem(@PathVariable Integer id) {
		
		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		Optional<FoodItem> foodItemWrapper = foodItemService.get(id);
		if (!foodItemWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		FoodItem foodItem = foodItemWrapper.get();

		if (!foodItem.getCreator().equals(userWrapper.get())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(foodItem, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	ResponseEntity<Collection<FoodItem>> getUserFoodItems() {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		List<FoodItem> foodItems = foodItemService.getAll(userWrapper.get());
		return new ResponseEntity<>(foodItems, HttpStatus.OK);

	}

	@RequestMapping(value = "/searchFood", method = RequestMethod.GET)
	ResponseEntity<Collection<FoodItem>> searchFood(@RequestParam("query") String query,
			@RequestParam("foodSource") String inputFoodSource) {

		logger.info("searchFood: souce; {}, query; {}", inputFoodSource, query);

		Optional<FoodSource> foodSourceWrapper = foodSourceService.findByName(inputFoodSource);
		if (!foodSourceWrapper.isPresent())
			return new ResponseEntity<Collection<FoodItem>>(HttpStatus.METHOD_NOT_ALLOWED);

		IFoodSourceHandler foodSourceHandler = foodSourceHelper.getFoodSourceHandler(foodSourceWrapper.get());

		List<FoodItem> foundFoodItems = foodSourceHandler.search(query);
		return new ResponseEntity<Collection<FoodItem>>(foundFoodItems, HttpStatus.OK);
	}

	@RequestMapping(value = "/createFoodItem", method = RequestMethod.POST)
	ResponseEntity<?> createFoodItem(@RequestBody FoodItem foodItem) {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		FoodItem createdFoodItem = foodItemService.save(foodItem);
		createdFoodItem.setCreator(userWrapper.get());
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateFoodItem/{id}", method = RequestMethod.POST)
	ResponseEntity<FoodItem> updateFoodItem(@PathVariable("id") Long id, @RequestBody FoodItem foodItem) {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		Optional<FoodItem> originalFoodItemWrapper = foodItemService.get(id);
		if (!originalFoodItemWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (!originalFoodItemWrapper.get().getCreator().equals(userWrapper.get())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		foodItem.setId(id);
		foodItem.setCreator(userWrapper.get());

		return new ResponseEntity<>(foodItemService.save(foodItem), HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteFoodItem/{id}", method = RequestMethod.DELETE)
	ResponseEntity<?> deleteFoodItem(@PathVariable("id") Long id) {
		
		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		Optional<FoodItem> originalFoodItemWrapper = foodItemService.get(id);
		if (!originalFoodItemWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (!originalFoodItemWrapper.get().getCreator().equals(userWrapper.get())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		foodItemService.delete(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	public static final Logger logger = LoggerFactory.getLogger(FoodItemApiController.class);

}
