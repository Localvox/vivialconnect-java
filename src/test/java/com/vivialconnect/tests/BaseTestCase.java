package com.vivialconnect.tests;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivialconnect.client.VivialConnectClient;

public class BaseTestCase{
	
	private static final int INVALID_ACCOUNT_ID = 0;
	private static final String INVALID_API_KEY = "";
	private static final String INVALID_API_SECRET = "";
	
	protected static final int ACCOUNT_ID = 10130;
	protected static final String API_KEY = "MTKUAQS6SS0STMWT8PBLD530VWA0AYNURN8";
	protected static final String API_SECRET = "N8TBkht8QHoDFw50HKNA1mf339cVyOgsQ9K89Gk8rWbVAwmr";
	
	
	@Test(expected = IllegalArgumentException.class)
	public static void client_initialization_with_invalid_account_id_throws_illegal_argument_exception(){
		VivialConnectClient.init(INVALID_ACCOUNT_ID, API_KEY, API_SECRET);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public static void client_initialization_with_invalid_api_key_throws_illegal_argument_exception(){
		VivialConnectClient.init(ACCOUNT_ID, INVALID_API_KEY, API_SECRET);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public static void client_initialization_with_invalid_api_secret_throws_illegal_argument_exception(){
		VivialConnectClient.init(ACCOUNT_ID, API_KEY, INVALID_API_SECRET);
	}
	
	
	protected <T> T loadFixture(String filename, Class<T> type){
		return loadFixture(filename, type, true);
	}
	
	
	protected <T> T loadFixture(String filename, Class<T> type, boolean unwrapRoot){
		ClassLoader classLoader = BaseTestCase.class.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(String.format("%s.json", filename));
		
		try{
			String content = IOUtils.toString(stream);
			
			ObjectMapper mapper = getObjectMapper();
			if (unwrapRoot){
	            mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
	        }
			
			return mapper.reader().forType(type).readValue(content);
		}
		catch (IOException ioe){
			System.err.println(String.format("Failed to load fixture ('%s'): %s", filename, ioe));
		}
		
		return null;
	}


	private ObjectMapper getObjectMapper(){
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
		return mapper;
	}
}