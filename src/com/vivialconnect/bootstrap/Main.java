package com.vivialconnect.bootstrap;

import com.vivialconnect.client.VivialConnectClient;
import com.vivialconnect.model.message.Message;

public class Main
{
	static final int ACCOUNT_ID = 10128;
	
	static final String API_KEY = "MTK4VVE0R6LH7BIQ9XNM6RIV45IFQY92MWE";
	static final String API_SECRET = "zTBzuvBeOWch0F6J6jMus6HaEwlbnX6XWRweoHDcmb97gauA";
	
	
	public static void main(String[] args) throws Exception
	{
		/* Message.send("+13132845729", "+18099667830", "Test message"); */
		
		VivialConnectClient.init(ACCOUNT_ID, API_KEY, API_SECRET);
		Message.getMessages();
	}
}