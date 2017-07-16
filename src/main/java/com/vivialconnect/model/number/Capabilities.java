package com.vivialconnect.model.number;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Capabilities{

    @JsonProperty
    private boolean sms;

    @JsonProperty
    private boolean mms;

    @JsonProperty
    private boolean voice;


    public boolean isSms(){
        return sms;
    }


    public void setSms(boolean sms){
        this.sms = sms;
    }


    public boolean isMms(){
        return mms;
    }


    public void setMms(boolean mms){
        this.mms = mms;
    }


    public boolean isVoice(){
        return voice;
    }


    public void setVoice(boolean voice){
        this.voice = voice;
    }
}