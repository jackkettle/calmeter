package com.calmeter.core.food.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.calmeter.core.food.model.Meal;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@Component
public class MealDeserializer extends JsonDeserializer<Meal> {

	@Override
	public Meal deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
		
		return null;

	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(Meal.class);

}
