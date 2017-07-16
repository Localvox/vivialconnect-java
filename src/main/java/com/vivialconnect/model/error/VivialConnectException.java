package com.vivialconnect.model.error;

public class VivialConnectException extends Exception{

    private static final long serialVersionUID = -5461533988163106640L;

    private int responseCode;


    public VivialConnectException() { 
    }


    public VivialConnectException(String message, Throwable cause){
        super(message, cause);
    }


    public VivialConnectException(Throwable cause){
        super(cause);
    }


    public int getResponseCode(){
        return responseCode;
    }


    public void setResponseCode(int responseCode){
        this.responseCode = responseCode;
    }
}