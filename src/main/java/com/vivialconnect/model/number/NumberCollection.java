package com.vivialconnect.model.number;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NumberCollection
{
	
	@JsonProperty("phone_numbers")
	private List<Number> numbers;
	
	
	public List<AvailableNumber> getAvailableNumbers()
	{
		List<AvailableNumber> availableNumbers = new ArrayList<AvailableNumber>(numbers.size());
		for (Number number : numbers)
		{
			availableNumbers.add((AvailableNumber) number);
		}
		
		return availableNumbers;
	}
	
	
	public List<AssociatedNumber> getAssociatedNumbers()
	{
		return null;
	}
	
	
	public void setNumbers(List<Number> numbers)
	{
		this.numbers = numbers;
	}
}