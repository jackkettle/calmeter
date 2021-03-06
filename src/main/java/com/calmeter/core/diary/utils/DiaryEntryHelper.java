package com.calmeter.core.diary.utils;

import java.util.Collection;

import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.utils.ClassHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;

@Component
public class DiaryEntryHelper {

    public static NutritionalInformation computeNutritionalInformation(Collection<DiaryEntry> diaryEntryList, NutritionalInfoType type) {
        NutritionalInformation totalNutritionalInformation = new NutritionalInformation(type);

        for (DiaryEntry diaryEntry : diaryEntryList) {
            diaryEntry.applyServingsModifiers();
            diaryEntry.computeNutritionalInformation();

            ClassHelper.addObjects(diaryEntry.getTotalNutritionalInformation(), totalNutritionalInformation);
        }
        return totalNutritionalInformation;
    }

    public static NutritionalInformation computeNutritionalInformation(DiaryEntry diaryEntry, NutritionalInfoType type) {
        NutritionalInformation totalNutritionalInformation = new NutritionalInformation(type);

        for (FoodItemEntry foodItemEntry : diaryEntry.getFoodItemEntries()) {
            ClassHelper.addObjects(foodItemEntry.getComputedNutritionalInformation(), totalNutritionalInformation);
        }

        return totalNutritionalInformation;
    }

    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(DiaryEntryHelper.class);

}
