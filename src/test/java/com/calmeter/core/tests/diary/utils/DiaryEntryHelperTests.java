package com.calmeter.core.tests.diary.utils;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.calmeter.core.food.model.FoodItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.diary.service.IDiaryEntryService;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.service.IFoodItemEntryService;
import com.calmeter.core.food.service.IFoodItemService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiaryEntryHelperTests {

    @Autowired
    IFoodItemService foodItemService;

    @Autowired
    IFoodItemEntryService foodItemEntryService;

    @Autowired
    IDiaryEntryService diaryEntryService;

    private DiaryEntry diaryEntry;

    @Before
    public void runBeforeTestMethod() throws Exception {

        Optional<FoodItem> bananaWrapper = foodItemService.get("Banana");
        Optional<FoodItem> eggWrapper = foodItemService.get("Egg, fried");

        if(!bananaWrapper.isPresent() || !eggWrapper.isPresent())
            throw new Exception("Initial food items not found");

        List<FoodItemEntry> foodItemEntries = new ArrayList<>();
        foodItemEntries.add(new FoodItemEntry(150.0, bananaWrapper.get()));
        foodItemEntries.add(new FoodItemEntry(50.0, eggWrapper.get()));

        for (FoodItemEntry entry : foodItemEntries) {
            foodItemEntryService.save(entry);
        }

        DiaryEntry diaryEntry = new DiaryEntry();
        diaryEntry.setFoodItemEntries(foodItemEntries);
        diaryEntry.setDateTime(LocalDateTime.now());
        this.diaryEntry = diaryEntryService.save(diaryEntry);

    }

    @Test
    public void addObjectsTest() throws Exception {

        diaryEntry.applyServingsModifiers();
        diaryEntry.computeNutritionalInformation();

        DiaryEntry diaryEntry = this.diaryEntry;
        double totalCals = 0.0;

        for (FoodItemEntry entry : diaryEntry.getFoodItemEntries()) {
            logger.info("Food: {}, cals: {}", entry.getFoodItemReference().getName(),
                    entry.getComputedNutritionalInformation().getCalories());
            totalCals += entry.getComputedNutritionalInformation().getCalories();
        }

        logger.info("totalCals: {}, totalNutritionCals: {}", totalCals,
                diaryEntry.getTotalNutrionalnformation().getCalories());
        assertEquals(0, Double.compare(totalCals, diaryEntry.getTotalNutrionalnformation().getCalories()));

    }

    private static Logger logger = LoggerFactory.getLogger(DiaryEntryHelperTests.class);

}
