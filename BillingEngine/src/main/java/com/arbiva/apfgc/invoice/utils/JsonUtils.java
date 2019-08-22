package com.arbiva.apfgc.invoice.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.arbiva.apfgc.invoice.dto.ErrorMessageDTO;
import com.arbiva.apfgc.invoice.exception.InvoiceEngineException;
import com.arbiva.apfgc.invoice.utils.InvoiceEngineErrorCode.InvoiceEngineErrorCodes;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link JsonUtils} holding the application specific constants.
 * 
 * @author srinivasa
 *
 */
public class JsonUtils {
	
	private static final Logger LOGGER = Logger.getLogger(JsonUtils.class);
	
	/**
	 * 
	 * @param json
	 * @param filePath
	 */
	public static void writeJson(Object json, String filePath) {
		if(StringUtils.isBlank(filePath)) {
			throw new InvoiceEngineException(new ErrorMessageDTO(InvoiceEngineErrorCodes.GAE002,
					".json file path should not be mull"));
		}
		try (FileWriter fileWritter = new FileWriter(filePath)) {
			fileWritter.write(new ObjectMapper().writerWithDefaultPrettyPrinter()
					.writeValueAsString(json));
			fileWritter.flush();
			LOGGER.info(String.format("Invoice json file is succesfully created for account number:%s on %s"));
		} catch (Exception e) {
			LOGGER.error(String.format("Error occured while creating invoice json file for account number:%s on %s"), e);
		}
	}
	
	/**
	 * 
	 * @param request
	 */
	public static void writeJson(InvoiceJsonRequest request) {
		validateInvoiceJsonRequest(request);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		String s = String.format("%s%s/%s/%s", request.getBaseFilePath(), request.getBillPeriod(), "jsons", request.getDistrictId());
		new File(s).mkdirs();
		try (FileWriter fileWritter = new FileWriter(request.getInvoiceJsonFilePath())) { 
			fileWritter.write(mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(request.getJson()));
			fileWritter.flush();
			LOGGER.info(String.format("Invoice json file is succesfully generated for acoount number:%s on %s",
					request.getAccountNumber(), request.getBillPeriod()));
		} catch (Exception e) {
			LOGGER.error(String.format("Error occured while creating invoice json file for account number:%s on %s",
					request.getAccountNumber(), request.getBillPeriod()), e);
		}
	}
	
	/**
	 * 
	 * @param filePath
	 * @param clazz
	 * @return
	 */
	public static <T> T readJson(String filePath, Class<T> clazz) {
		T t = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		try (InputStream stream = new FileInputStream(filePath)) {
			t = mapper.readValue(IOUtils.toString(stream, "UTF-8"), clazz);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mapper = null;
		}
		return t;
	}
	
	
	/**
	 * 
	 * @param request
	 */
	private static void validateInvoiceJsonRequest(InvoiceJsonRequest request) {
		if(request == null) {
			throw new InvoiceEngineException(new ErrorMessageDTO(InvoiceEngineErrorCodes.GAE002, 
					"Invoice Json Request should not be null to generate invoice json file"));
		}
		StringBuilder builder = new StringBuilder();
		if(request.getAccountNumber().isEmpty()) {
			builder.append("Account Number should not be null");
		}
		if(StringUtils.isBlank(request.getBaseFilePath())) {
			builder.append("Base File Path should not be null");
		}
		if(StringUtils.isBlank(request.getBillPeriod())) {
			builder.append("Bill Period should not be null");
		}
		if(request.getJson() == null) {
			builder.append("Json Object should not be null");
		}
		if(builder.length() > 0) {
			throw new InvoiceEngineException(new ErrorMessageDTO(InvoiceEngineErrorCodes.GAE002, 
					builder.toString()));
		}
	}
	
	  public static InputStream toUTF8InputStream(String str) {
	        InputStream is = null;
	        try {
	          is = new ByteArrayInputStream(str.getBytes("UTF-8"));
	        } catch (UnsupportedEncodingException e) {
	          // UTF-8 should always be supported
	          throw new AssertionError();
	        }
	        return is;
	      }
}
