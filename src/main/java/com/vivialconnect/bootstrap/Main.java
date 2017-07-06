package com.vivialconnect.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivialconnect.client.VivialConnectClient;
import com.vivialconnect.model.message.Attachment;
import com.vivialconnect.model.message.AttachmentCollection;
import com.vivialconnect.model.message.Message;
import com.vivialconnect.model.number.AssociatedNumber;
import com.vivialconnect.model.number.AvailableNumber;
import com.vivialconnect.model.number.Number;

public class Main
{
	private static final int NUMBER_ID = 131;

	static final int ACCOUNT_ID = 10130;
	
	static final String API_KEY = "MTKUAQS6SS0STMWT8PBLD530VWA0AYNURN8";
	static final String API_SECRET = "N8TBkht8QHoDFw50HKNA1mf339cVyOgsQ9K89Gk8rWbVAwmr";
	
	static final String FROM_NUMBER = "+13022133549";
	static final String TO_NUMBER = "+18099667830";
	
	
	public static void main(String[] args) throws Exception
	{
		VivialConnectClient.init(ACCOUNT_ID, API_KEY, API_SECRET);
		//sendMessage("Super Secret Message");
		//getMessageById(42248);
		//int numberId = 126, 127, 128, 129
		//int numberId = 130
		
		/* List<AvailableNumber> availableNumbers = getAvailableNumbers();
		AvailableNumber availableNumber = availableNumbers.get(0);
		AssociatedNumber associatedNumber = availableNumber.buy();
		System.out.println(associatedNumber.getId()); */
		
		//AssociatedNumber number = Number.buy(null, "302", "local");
		//List<AssociatedNumber> associatedNumbers = getAssociatedNumbers();
		//Number.count();
		
		/* AssociatedNumber number = getNumberById();
		number.setIncomingTextUrl("https://www.twitter.com/");
		number.setIncomingTextMethod("GET");
		number.update();
		
		System.out.println(number.getIncomingTextUrl());
		System.out.println(number.getIncomingTextMethod());*/
		
		/* AssociatedNumber number = getNumberById();
		number.delete(); */
		
		//Number.getLocalAssociatedNumbers();
		//Number.countLocal();
		//Number.getLocalNumberById(NUMBER_ID);
	}
	
	private static AssociatedNumber getNumberById()
	{
		return Number.getNumberById(NUMBER_ID);
	}
	
	
	private static List<AssociatedNumber> getAssociatedNumbers()
	{
		return Number.getAssociatedNumbers();
	}


	private static List<AvailableNumber> getAvailableNumbers()
	{
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("limit", "2");
		queryParams.put("in_city", "DOVER");
		
		return Number.findAvailableNumbersInRegion("DE", queryParams);
	}
	

	private static List<Attachment> getAttachments() throws IOException
	{
		ClassLoader classLoader = Main.class.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream("attachments.json");
		String fileContent = readFile(stream);
		
		ObjectMapper mapper = new ObjectMapper();
		AttachmentCollection collection = mapper.reader()
										        .forType(AttachmentCollection.class)
										        .readValue(fileContent);
		
		return collection.getAttachments();
	}
	
	
	private static String readFile(InputStream stream) throws IOException
	{
		return IOUtils.toString(stream);
	}
	
	
	private static void deleteAttachment(int attachmentId)
	{
		Attachment att = new Attachment();
		att.setMessageId(42248);
		att.setId(attachmentId);
		att.delete();
	}
	
	
	private static void attachmentCount(int messageId)
	{
		Attachment.count(messageId);
	}
	
	
	private static void getAttachmentById(int messageId, int attachmentId)
	{
		Attachment.getAttachmentById(messageId, attachmentId);
	}
	
	
	private static Message getMessageById(int messageId)
	{
		return Message.getMessageById(messageId);
	}
	
	
	private static void messageCount()
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
		message.setFromNumber(FROM_NUMBER);
		message.setToNumber(TO_NUMBER);
		message.setBody(body);
		message.send();
	}
}