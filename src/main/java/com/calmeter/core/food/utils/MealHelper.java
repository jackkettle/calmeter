package com.calmeter.core.food.utils;

import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.utils.DoubleHelper;
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

        NutritionalInformation totalNutritionalInformation = new NutritionalInformation(NutritionalInfoType.MEAL);

        for (FoodItemEntry foodItemEntry : meal.getFoodItemEntries()) {
            foodItemEntry.applyServingModifier();
            ClassHelper.addObjects(foodItemEntry.getComputedNutritionalInformation(), totalNutritionalInformation);
        }

        totalNutritionalInformation.setCalories(DoubleHelper.round(totalNutritionalInformation.getCalories(), 0));
        totalNutritionalInformation.setServingSize(DoubleHelper.round(totalNutritionalInformation.getServingSize(), 0));

        return totalNutritionalInformation;
    }

    public static Boolean isValid(Meal meal) {

        if (meal.getFoodItemEntries().size() < 1)
            return false;

        return true;
    }

    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(DiaryEntryHelper.class);

}
