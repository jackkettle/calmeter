package com.calmeter.core.food.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.calmeter.core.diary.utils.DiaryEntryHelper;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.model.Meal;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.utils.ClassHelper;

@Component
public class MealHelper {

	public static NutritionalInformation computeNutritionalInformation(Meal meal) {

		NutritionalInformation totalNutritionalInformation = new NutritionalInformation();

		for (FoodItemEntry foodItemEntry : meal.getFoodItemEntries()) {
			foodItemEntry.applyServingModifier();
			ClassHelper.addObjects(foodItemEntry.getComputedNutritionalInformation(), totalNutritionalInformation);
		}

		return totalNutritionalInformation;
	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(DiaryEntryHelper.class);

}
