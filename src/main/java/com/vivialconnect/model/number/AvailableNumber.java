package com.vivialconnect.model.number;

import com.vivialconnect.model.error.VivialConnectException;

public interface AvailableNumber extends INumber
{
	
	AssociatedNumber buy() throws VivialConnectException;
}