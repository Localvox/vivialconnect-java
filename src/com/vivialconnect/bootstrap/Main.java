package com.vivialconnect.bootstrap;

import com.vivialconnect.client.VivialConnectClient;
import com.vivialconnect.model.message.Message;

public class Main
{
	static final int ACCOUNT_ID = 10128;
	
	static final String API_KEY = "MTK4VVE0R6LH7BIQ9XNM6RIV45IFQY92MWE";
	static final String API_SECRET = "zTBzuvBeOWch0F6J6jMus6HaEwlbnX6XWRweoHDcmb97gauA";
	
	static final String FROM_NUMBER = "+13132845729";
	static final String TO_NUMBER = "+18099667830";
	
	
	public static void main(String[] args) throws Exception
	{
		VivialConnectClient.init(ACCOUNT_ID, API_KEY, API_SECRET);
		
		/* phoneNumber = Number.findAvailable(...).get(0).buy().getPhoneNumber(); */
		
		/* message.send().redact(); */
		
		/* TODO: HANDLE UNICODE CHARACTERS */
		
		Message message = new Message();
		message.setFromNumber(FROM_NUMBER);
		message.setToNumber(TO_NUMBER);
		message.setBody("☹.");
		message.send();
		
		/* message.sendWithAttachment(); */
	}
}