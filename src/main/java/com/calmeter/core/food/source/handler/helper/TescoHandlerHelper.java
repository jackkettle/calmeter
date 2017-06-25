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
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.micro.MineralLabel;
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

		logger.info(json);

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
			productNumbers.add(getFoodItem(objNode));
		}
		return productNumbers;

	}

	public static FoodItem getFoodItem(JsonNode productNode) {

		logger.debug("Getting foodItem");
		try {

			NutritionalInformation nutritionalInformation = new NutritionalInformation();
			FoodItem foodItem = new FoodItem();

			JsonNode nutritionNode = productNode.get("calcNutrition");

			Optional<Double> servingSizeWrapper = getServingSizeFromString(
					nutritionNode.get("perServingHeader").asText());
			if (!servingSizeWrapper.isPresent()) {
				logger.debug("Unable to get serving size from item");
				nutritionalInformation.setServingSize(100.0);
			} else {
				logger.info("Serving size: {}", servingSizeWrapper.get());
				nutritionalInformation.setServingSize(servingSizeWrapper.get());
			}

			Double calories = nutritionNode.get("calcNutrients").get(1).get("valuePer100").asDouble();
			nutritionalInformation.setCalories(calories);

			nutritionalInformation.getConsolidatedCarbs().setSugar(14.0);
			nutritionalInformation.getConsolidatedCarbs().setTotal(27.0);
			nutritionalInformation.getConsolidatedFats().setCholesterol(0.0);
			nutritionalInformation.getConsolidatedFats().setSaturatedFat(0.1);
			nutritionalInformation.getConsolidatedFats().setTotalFat(0.4);

			nutritionalInformation.getConsolidatedProteins().setProtein(1.3);
			nutritionalInformation.getMineralMap().put(MineralLabel.SODIUM, 1.2);

			foodItem.setName("Banana");
			foodItem.setWeightInGrams(118);
			foodItem.setNutritionalInformation(nutritionalInformation);

			System.exit(0);
			return foodItem;
		} catch (Exception e) {
			logger.error("Unable to get foodItem from JSON", e);
			return null;
		}

	}

	private static Optional<Double> getServingSizeFromString(String inputString) {
		Matcher m = Pattern.compile("\\((.*?)\\)").matcher(inputString);
		while (m.find()) {
			String result = m.group(1);
			result = result.replace("g", "");
			return Optional.of(Double.parseDouble(result));
		}
		return Optional.empty();
	}

	private static Logger logger = LoggerFactory.getLogger(TescoHandlerHelper.class);

}
