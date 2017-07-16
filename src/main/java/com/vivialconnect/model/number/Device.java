package com.vivialconnect.model.number;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Device{
	
    /** Information about errors in device lookup. For example, number is not mobile or carrier doesn’t support device lookup */
    @JsonProperty
    private String error;


    /** Model name of the device */
    @JsonProperty
    private String model;


    public String getError(){
        return error;
    }


    public void setError(String error){
        this.error = error;
    }


    public String getModel(){
        return model;
    }


    public void setModel(String model){
        this.model = model;
    }
}