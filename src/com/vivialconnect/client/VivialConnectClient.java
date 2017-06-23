package com.vivialconnect.client;

public final class VivialConnectClient
{

	private static int accountId;

	private static String	apiKey;
	private static String	apiSecret;
	
	public static final String API_BASE = "https://api.vivialconnect.net/api/v1.0";

	
	private VivialConnectClient()
	{
		// TODO Auto-generated constructor stub
	}


	public static String getApiKey()
	{
		return apiKey;
	}


	public static String getApiSecret()
	{
		return apiSecret;
	}


	public static int getAccountId()
	{
		return accountId;
	}
	

	public static void init(int accountId, String apiKey, String apiSecret)
	{
		VivialConnectClient.accountId = accountId;
		VivialConnectClient.apiKey = apiKey;
		VivialConnectClient.apiSecret = apiSecret;
	}
}