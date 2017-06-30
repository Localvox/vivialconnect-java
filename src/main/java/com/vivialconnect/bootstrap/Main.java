package com.vivialconnect.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		//sendMessage("Test with media_urls and connector_id fields");
		//getMessageId(42248);
	}
	
	
	private static void getMessageId(int messageId)
	{
		Message.getMessageById(messageId);
	}
	
	
	private static void count()
	{
		System.out.println(Message.count());
	}
	
	
	private static void getMessages()
	{
		Map<String, String> filters = new HashMap<String, String>();
		filters.put("limit", "1");
		
		List<Message> messages = Message.getMessages(filters);
		System.out.println(messages.size());
	}
	
	
	private static void sendMessage(String body)
	{
		Message message = new Message();
		message.addMediaUrl("http://blog.biakelsey.com/wp-content/uploads/6McYbbXo.jpg");
		message.addMediaUrl("https://s3-media1.fl.yelpcdn.com/bphoto/ouwCRgUnznnUhHZZHZBjqQ/ls.jpg");
		message.setFromNumber(FROM_NUMBER);
		message.setToNumber(TO_NUMBER);
		message.setBody(body);
		message.send();
	}
}