package com.calmeter.core.json;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

	@Override
	public void serialize(LocalDate localDate, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		ZoneId zoneId = ZoneId.systemDefault();
		gen.writeNumber(localDate.atStartOfDay(zoneId).toEpochSecond());

	}
}
