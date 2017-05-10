package com.calmeter.core.food.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.macro.carb.ConsolidatedCarbs;
import com.calmeter.core.food.model.nutrient.macro.fat.ConsolidatedFats;
import com.calmeter.core.food.model.nutrient.macro.protein.ConsolidatedProteins;
import com.calmeter.core.food.model.nutrient.micro.MineralLabel;
import com.calmeter.core.food.model.nutrient.micro.VitaminLabel;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class FoodItemDeserializer
		extends JsonDeserializer<FoodItem> {

	@Override
	public FoodItem deserialize (JsonParser jsonParser, DeserializationContext context)
			throws IOException {

		JsonNode rootNode = jsonParser.getCodec ().readTree (jsonParser);
		FoodItem foodItem = new FoodItem ();

		foodItem.setName (rootNode.get ("name").asText ());
		foodItem.setWeightInGrams (rootNode.get ("weightInGrams").asInt ());
		foodItem.setDescription (rootNode.get ("description").asText ());

		NutritionalInformation nutritionalInformation = new NutritionalInformation ();
		JsonNode nutritionalInformationNode = rootNode.get ("nutritionalInformation");

		nutritionalInformation.setCalories (nutritionalInformationNode.get ("calories").asDouble ());
		nutritionalInformation.setServingSize (nutritionalInformationNode.get ("servingSize").asDouble ());
		nutritionalInformation.setCaffeine (nutritionalInformationNode.get ("caffeine").asInt ());

		JsonNode consolidatedCarbsNode = rootNode.get ("nutritionalInformation");
		ConsolidatedCarbs consolidatedCarbs = new ConsolidatedCarbs ();

		if (consolidatedCarbsNode.get ("fiber") != null)
			consolidatedCarbs.setFiber (consolidatedCarbsNode.get ("fiber").asDouble ());

		if (consolidatedCarbsNode.get ("starch") != null)
			consolidatedCarbs.setStarch (consolidatedCarbsNode.get ("starch").asDouble ());

		if (consolidatedCarbsNode.get ("sugarAlcohol") != null)
			consolidatedCarbs.setSugarAlcohol (consolidatedCarbsNode.get ("sugarAlcohol").asDouble ());

		if (consolidatedCarbsNode.get ("sugar") != null)
			consolidatedCarbs.setSugar (consolidatedCarbsNode.get ("sugar").asDouble ());

		JsonNode consolidatedFatsNode = nutritionalInformationNode.get ("consolidatedFats");
		ConsolidatedFats consolidatedFats = new ConsolidatedFats ();

		if (consolidatedFatsNode.get ("saturatedFat") != null)
			consolidatedFats.setSaturatedFat (consolidatedFatsNode.get ("saturatedFat").asDouble ());

		if (consolidatedFatsNode.get ("monoUnsaturatedFat") != null)
			consolidatedFats.setMonoUnsaturatedFat (consolidatedFatsNode.get ("monoUnsaturatedFat").asDouble ());

		if (consolidatedFatsNode.get ("polyUnsaturatedFat") != null)
			consolidatedFats.setPolyUnsaturatedFat (consolidatedFatsNode.get ("polyUnsaturatedFat").asDouble ());

		if (consolidatedFatsNode.get ("transFat") != null)
			consolidatedFats.setTransFat (consolidatedFatsNode.get ("transFat").asDouble ());

		if (consolidatedFatsNode.get ("cholesterol") != null)
			consolidatedFats.setCholesterol (consolidatedFatsNode.get ("cholesterol").asDouble ());

		JsonNode consolidatedProteinsNode = nutritionalInformationNode.get ("consolidatedProteins");
		ConsolidatedProteins consolidatedProteins = new ConsolidatedProteins ();

		if (consolidatedProteinsNode.get ("protein") != null)
			consolidatedProteins.setProtein (consolidatedProteinsNode.get ("protein").asDouble ());

		Map<MineralLabel, Double> mineralMap = new HashMap<> ();
		Iterator<Entry<String, JsonNode>> mineralIterator = nutritionalInformationNode.get ("mineralMap").fields ();
		while (mineralIterator.hasNext ()) {
			Entry<String, JsonNode> entry = mineralIterator.next ();
			MineralLabel mineralLabel = MineralLabel.valueOf (entry.getKey ());
			mineralMap.put (mineralLabel, entry.getValue ().asDouble ());
		}
		nutritionalInformation.setMineralMap (mineralMap);

		Map<VitaminLabel, Double> vitaminMap = new HashMap<> ();
		Iterator<Entry<String, JsonNode>> vitaminIterator = nutritionalInformationNode.get ("vitaminMap").fields ();
		while (vitaminIterator.hasNext ()) {
			Entry<String, JsonNode> entry = vitaminIterator.next ();
			VitaminLabel mineralLabel = VitaminLabel.valueOf (entry.getKey ());
			vitaminMap.put (mineralLabel, entry.getValue ().asDouble ());
		}
		nutritionalInformation.setVitaminMap (vitaminMap);

		foodItem.setNutritionalInformation (nutritionalInformation);

		return foodItem;
	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger (FoodItemDeserializer.class);

}
