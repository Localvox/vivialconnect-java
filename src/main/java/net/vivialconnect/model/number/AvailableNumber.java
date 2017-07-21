package net.vivialconnect.model.number;

import net.vivialconnect.model.error.VivialConnectException;

public interface AvailableNumber extends INumber{
	
    AssociatedNumber buy() throws VivialConnectException;
}