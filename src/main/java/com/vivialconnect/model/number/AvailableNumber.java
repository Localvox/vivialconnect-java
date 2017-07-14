package com.vivialconnect.model.number;

import com.vivialconnect.model.VivialConnectException;

public interface AvailableNumber extends INumber
{
	
	AssociatedNumber buy() throws VivialConnectException;
}