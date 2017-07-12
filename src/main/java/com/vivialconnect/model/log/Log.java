package com.vivialconnect.model.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivialconnect.model.VivialConnectResource;

public class Log extends VivialConnectResource
{

	private static final long serialVersionUID = -1982193020990089235L;
	
	
	@JsonProperty
	private int id;
	
}