package com.arbiva.apfgc.invoice.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDateSerializer extends JsonSerializer<String>{
	
	private static final Logger LOGGER = Logger.getLogger(JsonDateSerializer.class);
	public JsonDateSerializer() {
		super();
	}
	
	@Override
	public void serialize(String strDate, JsonGenerator gen, SerializerProvider provider)
	throws IOException, JsonProcessingException {
		DateFormat to   = new SimpleDateFormat("dd-MM-yyyy"); // wanted format
		DateFormat from = new SimpleDateFormat("yyyy-MM-dd"); // current format
		try {
			gen.writeString(to.format(from.parse(strDate)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
