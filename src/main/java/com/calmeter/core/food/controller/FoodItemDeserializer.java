package com.calmeter.core.food.controller;

import java.io.IOException;

import com.calmeter.core.food.model.FoodItem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class FoodItemDeserializer extends JsonDeserializer<FoodItem> {

	@Override
	public FoodItem deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {

		JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
		FoodItem foodItem = populateTopLevelValues(rootNode);

		// TODO: Complete this method
		// Read in nutritional info
		// consolidatedCarbs
		// consolidatedFats
		// consolidatedProteins
		// vitaminMap
		// mineralMap

		return foodItem;
	}

	private FoodItem populateTopLevelValues(JsonNode rootNode) {
		FoodItem foodItem = new FoodItem();

		String name = rootNode.get("name").asText();
		foodItem.setName(name);

		return foodItem;
	}

}
