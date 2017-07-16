package com.vivialconnect.client;

import java.util.HashMap;
import java.util.Map;
import com.vivialconnect.model.message.Message;
import com.vivialconnect.model.message.MessageCollection;
    
public final class VivialConnectClient{

    private static int accountId;

    private static String apiKey;
    private static String apiSecret;

    public static final String API_BASE = "https://api.vivialconnect.net/api/v1.0";

    private static Map<Class<?>, Class<?>> collectionToInstanceMap;
	
	
    private VivialConnectClient(){

    }


    public static String getApiKey(){
        return apiKey;
    }


    public static String getApiSecret(){
        return apiSecret;
    }


    public static int getAccountId(){
        return accountId;
    }


    public static void init(int accountId, String apiKey, String apiSecret){
        //TODO: Validate API parameters

        VivialConnectClient.accountId = accountId;
        VivialConnectClient.apiKey = apiKey;
        VivialConnectClient.apiSecret = apiSecret;

        initMap();
    }


    private static void initMap(){
        if (collectionToInstanceMap == null){
            collectionToInstanceMap = new HashMap<Class<?>, Class<?>>();
            collectionToInstanceMap.put(MessageCollection.class, Message.class);
        }
    }


    public static Map<Class<?>, Class<?>> getCollectionToInstanceMap(){
        return collectionToInstanceMap;
    }
}