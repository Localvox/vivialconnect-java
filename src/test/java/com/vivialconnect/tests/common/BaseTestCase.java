package com.vivialconnect.tests.common;

import org.junit.Test;

import com.vivialconnect.client.VivialConnectClient;

public class BaseTestCase
{
	
	private static final int INVALID_ACCOUNT_ID = 0;
	private static final String INVALID_API_KEY = "";
	private static final String INVALID_API_SECRET = "";
	
	protected static final int ACCOUNT_ID = 10130;
	protected static final String API_KEY = "MTKUAQS6SS0STMWT8PBLD530VWA0AYNURN8";
	protected static final String API_SECRET = "N8TBkht8QHoDFw50HKNA1mf339cVyOgsQ9K89Gk8rWbVAwmr";
	
	
	@Test(expected = IllegalArgumentException.class)
	public void client_initialization_with_invalid_account_id_throws_illegal_argument_exception(){
		VivialConnectClient.init(INVALID_ACCOUNT_ID, API_KEY, API_SECRET);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void client_initialization_with_invalid_api_key_throws_illegal_argument_exception(){
		VivialConnectClient.init(ACCOUNT_ID, INVALID_API_KEY, API_SECRET);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void client_initialization_with_invalid_api_secret_throws_illegal_argument_exception(){
		VivialConnectClient.init(ACCOUNT_ID, API_KEY, INVALID_API_SECRET);
	}
	
	
	protected String loadFixture(String filename)
	{
		return null;
	}
}