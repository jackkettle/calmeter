package com.calmeter.core.food.source.handler.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.micro.MineralLabel;
import com.calmeter.core.utils.DoubleHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TescoHandlerHelper {

    public static Collection<String> getTescoProductNumbersFromJson(String json) {

        final ObjectMapper mapper = new ObjectMapper();
        Collection<String> productNumbers = new HashSet<>();

        JsonNode jsonRootNode;
        try {
            jsonRootNode = mapper.readTree(json);
        } catch (IOException e) {
            logger.error("Unable to read in response as json", e);
            return productNumbers;
        }
        JsonNode jsonResultsNode = jsonRootNode.at("/uk/ghs/products/results");
        for (final JsonNode objNode : jsonResultsNode) {
            productNumbers.add(objNode.get("tpnb").asText());
        }
        return productNumbers;

    }

    public static List<FoodItem> getFoodItemsFromResponse(String json) {

        final ObjectMapper mapper = new ObjectMapper();
        List<FoodItem> productNumbers = new ArrayList<>();

        JsonNode jsonRootNode;
        try {
            jsonRootNode = mapper.readTree(json);
        } catch (IOException e) {
            logger.error("Unable to read in response as json");
            return productNumbers;
        }
        JsonNode jsonResultsNode = jsonRootNode.at("/products");
        for (final JsonNode objNode : jsonResultsNode) {

            Optional<FoodItem> itemWrapper = getFoodItem(objNode);
            if (!itemWrapper.isPresent())
                continue;

            if (containsName(productNumbers, itemWrapper.get().getName()))
                continue;

            productNumbers.add(itemWrapper.get());
        }
        return productNumbers;

    }

    private static Optional<FoodItem> getFoodItem(JsonNode productNode) {

        try {

            NutritionalInformation nutritionalInformation = new NutritionalInformation(NutritionalInfoType.READ_ONLY);
            FoodItem foodItem = new FoodItem(FoodItemType.TESCO_ITEM);
            foodItem.setExternalId(productNode.get("tpnb").asLong());
            logger.debug("Getting foodItem: ID; {}", foodItem.getExternalId());

            Boolean isFood = productNode.at("/productCharacteristics/isFood").asBoolean();
            Boolean isDrink = productNode.at("/productCharacteristics/isDrink").asBoolean();

            if (!isFood && !isDrink) {
                logger.debug("The found product is not classified as food or drink. ID: {}", productNode.get("tpnb").asLong());
                return Optional.empty();
            }

            if (!productNode.has("calcNutrition")) {
                logger.debug("No calcNutrition node for id: {}", foodItem.getExternalId());
                return Optional.empty();
            }

            JsonNode nutritionNode = productNode.get("calcNutrition");

            boolean foundServingSize = false;
            if (nutritionNode.has("perServingHeader")) {

                Optional<Double> servingSizeWrapper = getServingSizeFromString(
                        nutritionNode.get("perServingHeader").asText());
                if (!servingSizeWrapper.isPresent()) {
                    logger.debug("Unable to get serving size from item");
                    nutritionalInformation.setServingSize(100.0);
                } else {
                    foundServingSize = true;
                    nutritionalInformation.setServingSize(servingSizeWrapper.get());
                }

            } else {
                logger.debug("Unable to get serving size from item");
                nutritionalInformation.setServingSize(100.0);
            }

            if (!nutritionNode.has("calcNutrients")) {
                logger.debug("No calcNutrients node for id: {}", foodItem.getExternalId());
                return Optional.empty();
            }

            JsonNode nutrientsNode = nutritionNode.get("calcNutrients");

            Double calories = nutrientsNode.get(1).get(VALUE_PER_100_KEY).asDouble();
            nutritionalInformation.setCalories(calories);

            if (!foundServingSize) {
                JsonNode calNode = nutrientsNode.get(1).get("valuePerServing");
                double caloriesPerServing = 0;
                double newServingSize = 0;
                if (nutrientsNode.get(1).get("valuePerServing") != null) {
                    caloriesPerServing = calNode.asDouble();
                    newServingSize = DoubleHelper.round((caloriesPerServing / calories) * 100, 0);
                    logger.info("New serving size: {}", newServingSize);
                }

                if (caloriesPerServing != 0) {
                    nutritionalInformation.setServingSize(newServingSize);
                }
            }

            Optional<Double> totalFatWrapper = getNodeDouble(nutrientsNode, 2, VALUE_PER_100_KEY);
            totalFatWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedFats().setTotalFat(aDouble));

            Optional<Double> saturatedFatWrapper = getNodeDouble(nutrientsNode, 3, VALUE_PER_100_KEY);
            saturatedFatWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedFats().setSaturatedFat(aDouble));

            Optional<Double> totalCarbsWrapper = getNodeDouble(nutrientsNode, 4, VALUE_PER_100_KEY);
            totalCarbsWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedCarbs().setTotal(aDouble));

            Optional<Double> sugarsWrapper = getNodeDouble(nutrientsNode, 5, VALUE_PER_100_KEY);
            sugarsWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedCarbs().setSugar(aDouble));

            Optional<Double> fiberWrapper = getNodeDouble(nutrientsNode, 6, VALUE_PER_100_KEY);
            fiberWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedCarbs().setFiber(aDouble));

            Optional<Double> proteinWrapper = getNodeDouble(nutrientsNode, 7, VALUE_PER_100_KEY);
            proteinWrapper.ifPresent(aDouble -> nutritionalInformation.getConsolidatedProteins().setProtein(aDouble));

            Optional<Double> sodiumWrapper = getNodeDouble(nutrientsNode, 8, VALUE_PER_100_KEY);
            sodiumWrapper.ifPresent(aDouble -> nutritionalInformation.getMineralMap().put(MineralLabel.SODIUM, aDouble));

            foodItem.setName(productNode.get("description").asText());
            foodItem.setNutritionalInformation(nutritionalInformation);
            foodItem.setFoodItemType(FoodItemType.TESCO_ITEM);

            return Optional.of(foodItem);

        } catch (Exception e) {
            logger.error("Unable to get foodItem from JSON. {}", e);
            return Optional.empty();
        }
    }

    private static Optional<Double> getNodeDouble(JsonNode nutrientsNode, int index, String key) {
        try {
            return Optional.of(nutrientsNode.get(index).get(key).asDouble());
        } catch (Exception e) {
            logger.warn("Unable to get node. Index; {}, Key; {}", index, key);
            return Optional.empty();
        }
    }

    private static boolean containsName(List<FoodItem> productNumbers, String name) {
        for (FoodItem item : productNumbers) {
            if (item.getName().equals(name))
                return true;
        }
        return false;
    }

    private static Optional<Double> getServingSizeFromString(String inputString) {
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(inputString);
        if (m.find()) {
            String result = m.group(1);
            result = result.replaceAll("[^0-9.]", "");
            return Optional.of(Double.parseDouble(result));
        }
        return Optional.empty();
    }

    private static String VALUE_PER_100_KEY = "valuePer100";

    private static Logger logger = LoggerFactory.getLogger(TescoHandlerHelper.class);

}
