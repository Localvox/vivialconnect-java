package com.vivialconnect.model.format;

import java.util.List;

public class ListFormatter implements JsonValueFormatter{
	
    private StringBuilder builder;


    public ListFormatter(){
        this.builder = new StringBuilder();
        this.builder.append("[");
    }


    @Override
    public String formatValue(Object value){
        List list = (List) value;

        for (Object object : list){
            JsonValueFormatter formatter = FormatterRegistry.getInstance().getFormatter(object.getClass());
            builder.append(formatter.formatValue(object)).append(",");
        }

        return builder.deleteCharAt(builder.length() - 1).append("]").toString();
    }
}