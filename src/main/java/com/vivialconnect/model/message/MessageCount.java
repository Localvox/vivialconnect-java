package com.vivialconnect.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageCount
{
	
	@JsonProperty
	private int count;
	

	public int getCount()
	{
		return count;
	}
	

	public void setCount(int count)
	{
		this.count = count;
	}
}