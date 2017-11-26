package com.calmeter.core.utils;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.model.Meal;
import com.calmeter.core.food.service.IFoodItemService;
import com.calmeter.core.food.source.handler.IFoodSourceHandler;
import com.calmeter.core.food.source.model.FoodSource;
import com.calmeter.core.food.source.service.IFoodSourceService;
import com.calmeter.core.food.source.utils.FoodSourceHelper;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class DeserializeHelper {

    @Autowired
    IFoodSourceService foodSourceService;

    @Autowired
    FoodSourceHelper foodSourceHelper;

    @Autowired
    IFoodItemService foodItemService;

    public List<FoodItemEntry> getFoodItemEntries(Iterator<JsonNode> foodItemsNodes, DiaryEntry diaryEntry) {
        return getFoodItemEntries(foodItemsNodes, diaryEntry, null);
    }

    public List<FoodItemEntry> getFoodItemEntries(Iterator<JsonNode> foodItemsNodes, Meal meal) {
        return getFoodItemEntries(foodItemsNodes, null, meal);
    }


    private List<FoodItemEntry> getFoodItemEntries(Iterator<JsonNode> foodItemsNodes, DiaryEntry diaryEntry, Meal meal) {
        List<FoodItemEntry> foodItemEntries = new ArrayList<>();
        while (foodItemsNodes.hasNext()) {
            JsonNode foodItemNode = foodItemsNodes.next();

            Double servings = foodItemNode.get("servings").asDouble();

            Integer id = foodItemNode.get("id").asInt();
            String foodSourceString = foodItemNode.get("source").asText();

            Optional<FoodSource> foodSourceWrapper = foodSourceService.findByName(foodSourceString);
            if (!foodSourceWrapper.isPresent()) {
                logger.error("Unable to get food source: {}", foodSourceString);
                continue;
            }

            IFoodSourceHandler foodSourceHandler = foodSourceHelper.getFoodSourceHandler(foodSourceWrapper.get());
            logger.info("Getting foodItem using source and Id: {}, {}", foodSourceHandler.getClass(), id.longValue());
            Optional<FoodItem> foodItemWrapper = foodSourceHandler.getItemFromID(id.longValue());

            if (!foodItemWrapper.isPresent()) {
                logger.error("Unable to get FoodItem from ID: {}, source: {}", id.longValue(), foodSourceString);
                continue;
            }

            FoodItem foodItem = foodItemWrapper.get();
            Double weightInGrams = servings * foodItem.getNutritionalInformation().getServingSize();

            if (foodItem.getId() == null && foodItem.getExternalId() == null) {
                logger.info("Adding foodItem to db as it does not already exist: {}", foodItem.getExternalId());
                foodItemService.save(foodItem);
            }

            FoodItemEntry foodItemEntry = new FoodItemEntry();
            foodItemEntry.setFoodItem(foodItem);
            foodItemEntry.setWeightInGrams(weightInGrams);

            if(diaryEntry != null)
                foodItemEntry.setDiaryEntry(diaryEntry);

            if(diaryEntry != null)
                foodItemEntry.setMeal(meal);

            foodItemEntries.add(foodItemEntry);
        }

        return foodItemEntries;
    }

    public static final Logger logger = LoggerFactory.getLogger(DeserializeHelper.class);

}
