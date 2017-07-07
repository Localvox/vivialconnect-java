package com.vivialconnect.model.number;

import java.util.Date;

public interface AssociatedNumber extends INumber
{
	
	AssociatedNumber update();
	
	
	AssociatedNumber updateLocalNumber();
	
	
	boolean delete();
	
	
	boolean deleteLocalNumber();
	
	
	NumberInfo lookup();
	
	
	int getId();
	
	
	void setId(int id);
	
	
	Date getDateCreated();
	
	
	void setDateCreated(Date dateCreated);
	
	
	Date getDateModified();
	
	
	void setDateModified(Date dateModified);
	
	
	int getAccountId();
	
	
	void setAccountId(int accountId);
	
	
	String getVoiceForwardingNumber();
	
	
	void setVoiceForwardingNumber(String voiceForwardingNumber);
	
	
	Capabilities getCapabilities();
	
	
	void setCapabilities(Capabilities capabilities);
	
	
	boolean isActive();
	
	
	void setActive(boolean active);
	
	
	String getStatusTextUrl();
	
	
	String getIncomingTextUrl();
	
	
	String getIncomingTextMethod();
	
	
	String getIncomingTextFallbackUrl();
	
	
	String getIncomingTextFallbackMethod();
	
	
	int getConnectorId();
	
	
	void setLata(String lata);
	
	
	void setCity(String city);
	
	
	void setRegion(String region);
	
	
	void setRateCenter(String rateCenter);
}