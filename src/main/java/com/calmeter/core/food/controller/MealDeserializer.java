package com.calmeter.core.food.controller;

import java.io.IOException;
import java.util.Iterator;

import com.calmeter.core.utils.DeserializeHelper;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.food.model.Meal;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@Component
public class MealDeserializer extends JsonDeserializer<Meal> {

    @Autowired
    DeserializeHelper deserializeHelper;

    @Override
    public Meal deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {

        logger.info("Deserialize Meal item");
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        Meal meal = new Meal();
        meal.setName(rootNode.get("name").asText());

        meal.setDescription(rootNode.get("description").asText());

        Iterator<JsonNode> foodItemsNodes = rootNode.get("foodItemFormArray").elements();
        meal.setFoodItemEntries(deserializeHelper.getFoodItemEntries(foodItemsNodes, meal));

        return meal;

    }

    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(Meal.class);

}
