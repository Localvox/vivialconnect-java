package com.vivialconnect.model.format;

import com.vivialconnect.model.connector.PhoneNumber;

public class PhoneNumberFormatter implements JsonValueFormatter{

    @Override
    public String formatValue(Object value){
        PhoneNumber phoneNumber = (PhoneNumber) value;

        JsonBodyBuilder builder = JsonBodyBuilder.emptyJson();
        builder.addParamPair("phone_number", phoneNumber.getPhoneNumber());
        builder.addParamPair("phone_number_id", phoneNumber.getPhoneNumberId());

        return builder.build();
    }
}