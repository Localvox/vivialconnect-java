package com.vivialconnect.model;


public class NoContentException extends RuntimeException
{
	
	private static final long serialVersionUID = -6978518975975681292L;

	
	public NoContentException(String message)
	{
		super(message);
	}
}