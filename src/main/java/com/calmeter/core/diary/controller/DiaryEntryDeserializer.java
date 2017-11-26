package com.calmeter.core.diary.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.calmeter.core.utils.DeserializeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.service.IFoodItemService;
import com.calmeter.core.food.source.handler.IFoodSourceHandler;
import com.calmeter.core.food.source.model.FoodSource;
import com.calmeter.core.food.source.service.IFoodSourceService;
import com.calmeter.core.food.source.utils.FoodSourceHelper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class DiaryEntryDeserializer extends JsonDeserializer<DiaryEntry> {

	@Autowired
	IFoodSourceService foodSourceService;

	@Autowired
	IFoodItemService foodItemService;

	@Autowired
	FoodSourceHelper foodSourceHelper;

	@Autowired
	DeserializeHelper deserializeHelper;

	@Override
	public DiaryEntry deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
		DiaryEntry diaryEntry = new DiaryEntry();

		String date = rootNode.get("date").asText();
		String time = rootNode.get("time").asText();

		String fullDateTimeString = date + " " + time;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm a");
		LocalDateTime dateTime = LocalDateTime.parse(fullDateTimeString, formatter);

		diaryEntry.setDateTime(dateTime);

		Iterator<JsonNode> foodItemsNodes = rootNode.get("foodItemFormArray").elements();
		diaryEntry.setFoodItemEntries(deserializeHelper.getFoodItemEntries(foodItemsNodes, diaryEntry));

		return diaryEntry;
	}

	private static Logger logger = LoggerFactory.getLogger(DiaryEntryDeserializer.class);

}
