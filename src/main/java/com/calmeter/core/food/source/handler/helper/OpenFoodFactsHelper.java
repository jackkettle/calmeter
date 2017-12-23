package com.calmeter.core.food.source.handler.helper;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemType;
import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.micro.MineralLabel;
import com.calmeter.core.food.source.handler.helper.utils.JsonNodeUtils;
import com.calmeter.core.utils.ConversionHelper;
import com.calmeter.core.utils.DoubleHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OpenFoodFactsHelper {

    public static Optional<FoodItem> getFoodItemFromResponse(String json) {

        final ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonRootNode;
        try {
            jsonRootNode = mapper.readTree(json);
        } catch (IOException e) {
            logger.error("Unable to read in response as json", e);
            return Optional.empty();
        }
        return getFoodItemFromNode(jsonRootNode);
    }

    public static List<FoodItem> getFoodItemsFromResponse(String json) {

        final ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonRootNode;
        try {
            jsonRootNode = mapper.readTree(json);
        } catch (IOException e) {
            logger.error("Unable to read in response as json", e);
            return new ArrayList<>();
        }
        return getFoodItemsFromNode(jsonRootNode);
    }

    private static Optional<FoodItem> getFoodItemFromNode(JsonNode jsonRootNode) {
        JsonNode jsonProductNode = jsonRootNode.at("/product");
        return getFoodItem(jsonProductNode);
    }

    private static List<FoodItem> getFoodItemsFromNode(JsonNode jsonRootNode) {

        JsonNode jsonProductNode = jsonRootNode.at("/products");
        List<FoodItem> foodItems = new ArrayList<>();
        int index = 0;
        for (final JsonNode objNode : jsonProductNode) {
            logger.info("Index: {}", ++index);
            Optional<FoodItem> itemWrapper = getFoodItem(objNode);
            itemWrapper.ifPresent(foodItems::add);
        }
        return foodItems;
    }

    private static Optional<FoodItem> getFoodItem(JsonNode jsonRootNode) {

        NutritionalInformation nutritionalInformation = new NutritionalInformation(NutritionalInfoType.READ_ONLY);
        FoodItem foodItem = new FoodItem(FoodItemType.OPEN_FOOD_FACTS_ITEM);

        JsonNode jsonProductNode = jsonRootNode;

        String productNameNodeKey = "product_name";
        if (!jsonProductNode.has(productNameNodeKey))
            return Optional.empty();

        foodItem.setName(jsonProductNode.get(productNameNodeKey).asText());

        String productQuantityNodeKey = "quantity";
        if (jsonProductNode.has(productQuantityNodeKey))
            foodItem.setDescription(jsonProductNode.get(productQuantityNodeKey).asText());

        String nutrimentsNodeKey = "nutriments";
        if (!jsonProductNode.has(nutrimentsNodeKey))
            return Optional.empty();

        String gtinNodeKey = "code";
        if (!jsonProductNode.has(nutrimentsNodeKey))
            return Optional.empty();

        foodItem.setGtin(jsonProductNode.get(gtinNodeKey).asLong());

        Optional<Double> servingWrapper = JsonNodeUtils.getNodeDouble(jsonProductNode, "serving_quantity");
        servingWrapper.ifPresent(aDouble -> nutritionalInformation.setServingSize(DoubleHelper.round(aDouble, 0)));

        JsonNode nutritionNode = jsonProductNode.get(nutrimentsNodeKey);

        boolean kcal = false;
        String energyUnitKey = "energy_unit";
        if (nutritionNode.has(energyUnitKey)) {
            kcal = nutritionNode.get(energyUnitKey).asText().equals("kcal");
        }

        Optional<Double> energyWrapper = JsonNodeUtils.getNodeDouble(nutritionNode, "energy_value");
        if (kcal)
            energyWrapper.ifPresent(aDouble -> nutritionalInformation.setCalories(aDouble));
        else
            energyWrapper.ifPresent(aDouble -> nutritionalInformation.setCalories(ConversionHelper.convertKjToKcals(aDouble)));

        Optional<Double> saturatedFatWrapper = JsonNodeUtils.getNodeDouble(nutritionNode, "saturated-fat_100g");
        saturatedFatWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedFats().setSaturatedFat(DoubleHelper.round(aDouble, 2)));

        Optional<Double> fatWrapper = JsonNodeUtils.getNodeDouble(nutritionNode, "fat_100g");
        fatWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedFats().setTotalFat(DoubleHelper.round(aDouble, 2)));

        Optional<Double> sugarsWrapper = JsonNodeUtils.getNodeDouble(nutritionNode, "sugars_100g");
        sugarsWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedCarbs().setSugar(DoubleHelper.round(aDouble, 2)));

        Optional<Double> carbsWrapper = JsonNodeUtils.getNodeDouble(nutritionNode, "carbohydrates_100g");
        carbsWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedCarbs().setTotal(DoubleHelper.round(aDouble, 2)));

        Optional<Double> proteinsWrapper = JsonNodeUtils.getNodeDouble(nutritionNode, "proteins_100g");
        proteinsWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedProteins().setProtein(DoubleHelper.round(aDouble, 2)));

        Optional<Double> sodiumWrapper = JsonNodeUtils.getNodeDouble(nutritionNode, "sodium_100g");
        sodiumWrapper.ifPresent(aDouble -> nutritionalInformation.getMineralMap().put(MineralLabel.SODIUM, DoubleHelper.round(aDouble, 2)));

        foodItem.setNutritionalInformation(nutritionalInformation);

        if (!nutritionalInformation.isValid()) {
            logger.error("FoodItem is invalid");
            return Optional.empty();
        }

        return Optional.of(foodItem);
    }

    private static Logger logger = LoggerFactory.getLogger(OpenFoodFactsHelper.class);

}
