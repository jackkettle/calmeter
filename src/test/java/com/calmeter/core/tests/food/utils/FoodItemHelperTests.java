package com.calmeter.core.tests.food.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.service.IFoodItemService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodItemHelperTests {

	@Autowired
	IFoodItemService foodItemService;

	@Test
	public void applyServingModifierTest() throws Exception {

		FoodItemEntry foodItemEntry = new FoodItemEntry();
		FoodItem foodItem = foodItemService.get("Egg, fried").get();

		foodItemEntry.setFoodItemReference(foodItem);
		foodItemEntry.setWeightInGrams(75.0);

		logger.info("Cals before: {}", foodItem.getNutritionalInformation().getCalories());
		foodItemEntry.applyServingModifier();
		logger.info("Cals after: {}", foodItemEntry.getComputedNutritionalInformation().getCalories());

		assertEquals(0, Double.compare(146.25, foodItemEntry.getComputedNutritionalInformation().getCalories()));

	}

	private static Logger logger = LoggerFactory.getLogger(FoodItemHelperTests.class);

}
