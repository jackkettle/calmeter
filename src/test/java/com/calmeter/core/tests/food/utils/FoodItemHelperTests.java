package com.calmeter.core.tests.food.utils;

import static org.junit.Assert.*;

import com.calmeter.core.ApplicationConfig;
import com.calmeter.core.Main;
import com.calmeter.core.custom.TestValueLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.service.IFoodItemService;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@ContextConfiguration(classes =  {Main.class, ApplicationConfig.class})
public class FoodItemHelperTests extends AbstractTestNGSpringContextTests {

	@Autowired
	IFoodItemService foodItemService;

	@Autowired
	TestValueLoader testValueLoader;

	@BeforeClass
	public void setUp(){
		testValueLoader.loadValues();
	}

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
