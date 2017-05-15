package com.calmeter.core.diary.controller;

import java.io.IOException;
import java.util.List;

import com.calmeter.core.food.model.FoodItem;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class FoodItemListLiteSerializer extends JsonSerializer<List<FoodItem>> {

	@Override
	public void serialize(List<FoodItem> foodItems, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		gen.writeStartArray();
		for (FoodItem foodItem : foodItems) {
			gen.writeStartObject();
			gen.writeNumberField("id", foodItem.getId());
			gen.writeStringField("name", foodItem.getName());
			gen.writeNumberField("weightInGrams", foodItem.getWeightInGrams());
			gen.writeEndObject();
		}
		gen.writeEndArray();

	}
}
