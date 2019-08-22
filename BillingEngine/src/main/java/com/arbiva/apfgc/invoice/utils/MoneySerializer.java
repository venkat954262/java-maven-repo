package com.arbiva.apfgc.invoice.utils;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MoneySerializer extends JsonSerializer<BigDecimal> {
	
	private static final Logger LOGGER = Logger.getLogger(MoneySerializer.class);
	public MoneySerializer() {
		super();
	}

	@Override
	public void serialize(BigDecimal value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		// put your desired money style here
		jgen.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
	}

}
