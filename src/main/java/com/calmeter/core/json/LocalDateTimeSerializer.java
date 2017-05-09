package com.calmeter.core.json;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	@Override
	public void serialize(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		ZoneId id = ZoneId.systemDefault();
		ZonedDateTime zdt = ZonedDateTime.of(localDateTime, id);
		gen.writeNumber(zdt.toInstant().toEpochMilli());

	}
}
