package com.calmeter.core.diary.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.source.handler.IFoodSourceHandler;
import com.calmeter.core.food.source.model.FoodSource;
import com.calmeter.core.food.source.utils.FoodSourceHelper;
import com.calmeter.core.spring.ApplicationContextProvider;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class DiaryEntryDeserializer
		extends JsonDeserializer<DiaryEntry> {

	@Autowired
	FoodSourceHelper foodSourceHelper;

	@Override
	public DiaryEntry deserialize (JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		JsonNode rootNode = jsonParser.getCodec ().readTree (jsonParser);
		DiaryEntry diaryEnty = new DiaryEntry ();

		String date = rootNode.get ("date").asText ();
		String time = rootNode.get ("time").asText ();

		String fullDateTimeString = date + " " + time;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("dd/MM/yyyy h:mm a");
		LocalDateTime dateTime = LocalDateTime.parse (fullDateTimeString, formatter);

		diaryEnty.setTime (dateTime);

		Iterator<JsonNode> foodItemsNodes = rootNode.get ("foodItemFormArray").elements ();

		List<FoodItem> foodItems = new ArrayList<> ();
		while (foodItemsNodes.hasNext ()) {
			JsonNode foodItemNode = foodItemsNodes.next ();

			Integer id = foodItemNode.get ("id").asInt ();
			String foodSourceString = foodItemNode.get ("source").asText ();

			Optional<FoodSource> foodSourceWrapper = foodSourceHelper.getFoodSourceFromName (foodSourceString);
			if (!foodSourceWrapper.isPresent ()) {
				logger.error ("Unable to get food source: {}", foodSourceString);
				continue;
			}

			IFoodSourceHandler foodSourceHandler = getFoodSourceHandler(foodSourceWrapper.get ());
			FoodItem foodItem = foodSourceHandler.getItemFromID (Long.valueOf (id.longValue ()));

			foodItems.add (foodItem);
		}
		diaryEnty.setFoodItems (foodItems);

		return diaryEnty;
	}
	
	IFoodSourceHandler getFoodSourceHandler(FoodSource foodSource){
		IFoodSourceHandler foodSourceHandler = null;
		try {
			foodSourceHandler = foodSource.getSourceHandler ().newInstance ();
			AutowireCapableBeanFactory factory = ApplicationContextProvider.getApplicationContext ().getAutowireCapableBeanFactory ();

			factory.autowireBean (foodSourceHandler);
			factory.initializeBean (foodSourceHandler, "foodSourceHandler");

		}
		catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace ();
		}
		
		return foodSourceHandler;
	}

	private static Logger logger = LoggerFactory.getLogger (DiaryEntryDeserializer.class);

}
