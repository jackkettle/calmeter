package com.calmeter.core.food.source.handler.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			logger.error("Unable to read in response as json", e);
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

	public static Optional<FoodItem> getFoodItem(JsonNode productNode) {

		logger.debug("Getting foodItem");
		try {

			NutritionalInformation nutritionalInformation = new NutritionalInformation();
			FoodItem foodItem = new FoodItem();

			Boolean isFood = productNode.at("/productCharacteristics/isFood").asBoolean();
			Boolean isDrink = productNode.at("/productCharacteristics/isDrink").asBoolean();

			if (!isFood && !isDrink) {
				logger.info("The found product is not classified as food or drink. ID: {}",
						productNode.get("tpnb").asLong());
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

			JsonNode nutrientsNode = nutritionNode.get("calcNutrients");

			Double calories = nutrientsNode.get(1).get("valuePer100").asDouble();
			nutritionalInformation.setCalories(calories);

			if (!foundServingSize) {
				double caloriesPerServing = nutrientsNode.get(1).get("valuePerServing").asDouble();
				double newServingSize = DoubleHelper.round((caloriesPerServing / calories) * 100, 0);

				logger.info("New serving size: {}", newServingSize);

				if (caloriesPerServing != 0) {
					nutritionalInformation.setServingSize(newServingSize);
				}
			}

			Double totalFat = nutrientsNode.get(2).get("valuePer100").asDouble();
			nutritionalInformation.getConsolidatedFats().setTotalFat(totalFat);

			Double saturatedFat = nutrientsNode.get(3).get("valuePer100").asDouble();
			nutritionalInformation.getConsolidatedFats().setSaturatedFat(saturatedFat);

			Double totalCarbs = nutrientsNode.get(4).get("valuePer100").asDouble();
			nutritionalInformation.getConsolidatedCarbs().setTotal(totalCarbs);

			Double sugars = nutrientsNode.get(5).get("valuePer100").asDouble();
			nutritionalInformation.getConsolidatedCarbs().setSugar(sugars);

			Double fiber = nutrientsNode.get(6).get("valuePer100").asDouble();
			nutritionalInformation.getConsolidatedCarbs().setFiber(fiber);

			Double protein = nutrientsNode.get(7).get("valuePer100").asDouble();
			nutritionalInformation.getConsolidatedProteins().setProtein(protein);

			Double sodium = nutrientsNode.get(8).get("valuePer100").asDouble();
			nutritionalInformation.getMineralMap().put(MineralLabel.SODIUM, sodium);

			foodItem.setName(productNode.get("description").asText());
			foodItem.setExternalId(productNode.get("tpnb").asLong());
			foodItem.setNutritionalInformation(nutritionalInformation);
			foodItem.setFoodItemType(FoodItemType.TESCO_ITEM);

			return Optional.of(foodItem);
			
		} catch (Exception e) {
			logger.error("productNode: {}", productNode.toString());
			logger.error("Unable to get foodItem from JSON. {}", e.getMessage());
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
		while (m.find()) {
			String result = m.group(1);
			result = result.replaceAll("[^0-9\\.]", "");
			return Optional.of(Double.parseDouble(result));
		}
		return Optional.empty();
	}

	private static Logger logger = LoggerFactory.getLogger(TescoHandlerHelper.class);

}
