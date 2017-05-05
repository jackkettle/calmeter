package com.calmeter.core.diary.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.calmeter.core.diary.model.DiaryEntry;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class DiaryEntryDeserializer extends JsonDeserializer<DiaryEntry> {

	@Override
	public DiaryEntry deserialize (JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		
		JsonNode rootNode = jsonParser.getCodec ().readTree (jsonParser);
		DiaryEntry diaryEnty = new DiaryEntry ();

		logger.info (rootNode.toString ());
		
		String date = rootNode.get ("date").asText ();
		String time = rootNode.get ("time").asText ();
		
		String fullDateTimeString = date + " " + time;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm a");
		LocalDateTime dateTime = LocalDateTime.parse(fullDateTimeString, formatter);
		
		diaryEnty.setTime (dateTime);
		return diaryEnty;
	}
	
	private static Logger logger = LoggerFactory.getLogger (DiaryEntryDeserializer.class);


}
