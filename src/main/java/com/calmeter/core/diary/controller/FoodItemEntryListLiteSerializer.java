package com.calmeter.core.diary.controller;

import java.io.IOException;
import java.util.List;

import com.calmeter.core.food.model.FoodItemEntry;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class FoodItemEntryListLiteSerializer extends JsonSerializer<List<FoodItemEntry>> {

	@Override
	public void serialize(List<FoodItemEntry> foodItemEntries, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		gen.writeStartArray();
		for (FoodItemEntry foodItemEntry : foodItemEntries) {
			gen.writeStartObject();
			gen.writeNumberField("id", foodItemEntry.getFoodItemReference().getId());
			gen.writeStringField("name", foodItemEntry.getFoodItemReference().getName());
			gen.writeStringField("weightInGrams", foodItemEntry.getWeightInGrams().toString());
			gen.writeEndObject();
		}
		gen.writeEndArray();

	}
}
