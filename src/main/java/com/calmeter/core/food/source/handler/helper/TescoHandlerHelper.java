package com.calmeter.core.food.source.handler.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calmeter.core.food.model.FoodItem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TescoHandlerHelper {

	public static Collection<String> getTescoProductNumbersFromJson (String json) {

		final ObjectMapper mapper = new ObjectMapper ();
		Collection<String> productNumbers = new HashSet<> ();

		JsonNode jsonRootNode;
		try {
			jsonRootNode = mapper.readTree (json);
		}
		catch (IOException e) {
			logger.error ("Unable to read in response as json", e);
			return productNumbers;
		}
		JsonNode jsonResultsNode = jsonRootNode.at ("/uk/ghs/products/results");
		for (final JsonNode objNode : jsonResultsNode) {
			productNumbers.add (objNode.get ("tpnb").asText ());
		}
		return productNumbers;

	}
	
	
	public static List<FoodItem> getFoodItemsResponse (String json) {

		final ObjectMapper mapper = new ObjectMapper ();
		List<FoodItem> productNumbers = new ArrayList<> ();

		JsonNode jsonRootNode;
		try {
			jsonRootNode = mapper.readTree (json);
		}
		catch (IOException e) {
			logger.error ("Unable to read in response as json", e);
			return productNumbers;
		}
		JsonNode jsonResultsNode = jsonRootNode.at ("/products");
		for (final JsonNode objNode : jsonResultsNode) {
			productNumbers.add (getFoodItem(objNode));
		}
		return productNumbers;

	}
	
	public static FoodItem getFoodItem (JsonNode productNode) {

		

	}

	private static Logger logger = LoggerFactory.getLogger (TescoHandlerHelper.class);

}
