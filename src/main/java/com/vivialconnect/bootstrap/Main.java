package com.vivialconnect.bootstrap;

import com.vivialconnect.client.VivialConnectClient;
import com.vivialconnect.model.connector.Callback;
import com.vivialconnect.model.error.VivialConnectException;

public class Main{
    private static final int NUMBER_ID = 131;

    static final int ACCOUNT_ID = 10130;

    static final String API_KEY = "MTKUAQS6SS0STMWT8PBLD530VWA0AYNURN8";
    static final String API_SECRET = "N8TBkht8QHoDFw50HKNA1mf339cVyOgsQ9K89Gk8rWbVAwmr";

    static final String FROM_NUMBER = "+13022136859";
    static final String TO_NUMBER = "+18099667830";
	
    public static void main(String[] args)
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
        /* Number.getLocalNumberById(NUMBER_ID); */

        /* AssociatedNumber number = getNumberById();
        number.lookup(); */

        /* AssociatedNumber number = Number.getAssociatedNumbers().get(0);

        Message message = new Message();
        message.setFromNumber(number.getPhoneNumber());
        message.setToNumber(TO_NUMBER);
        message.setBody("Hello! 😊");
        message.send(); */

        /* Connector connector = new Connector();
        connector.setName("Dummy Connector");
        connector.create(); */

        //List<Connector> connectors = Connector.getConnectors(); 

        //System.out.println(Connector.count());

        /* Callback callback = new Callback();
        callback.setMessageType("text");
        callback.setEventType("status");
        callback.setMethod("POST");
        callback.setUrl("new/path/to/sms/incoming"); */

        /* Connector connector = Connector.getConnectorById(29);
        connector.deleteAllPhoneNumbers(); */

        //connector.addCallback(callback).createCallbacks();

        /* for (Callback callback : connector.getCallbacks())
        {
                connector.deleteSingleCallback(callback);
                callback.setUrl("new/path/to/sms/text");
        } */

        //PhoneNumber.getPhoneNumbers(29);

        try{
            Callback.count(29);
        }catch (VivialConnectException e){
            System.out.println();
        }
    }
}