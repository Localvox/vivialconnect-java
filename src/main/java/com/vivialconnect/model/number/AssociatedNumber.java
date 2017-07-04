package com.vivialconnect.model.number;

import java.util.Date;

public interface AssociatedNumber extends INumber
{
	
	int getId();
	
	
	void setId(int id);
	
	
	Date getDateCreated();
	
	
	void setDateCreated(Date dateCreated);
	
	
	Date getDateModified();
	
	
	void setDateModified(Date dateModified);
	
	
	int getAccountId();
	
	
	void setAccountId(int accountId);
	
	
	String getStatusTextUrl();
	
	
	void setStatusTextUrl(String statusTextUrl);
	
	
	String getIncomingTextUrl();
	
	
	void setIncomingTextUrl(String incomingTextUrl);
	
	
	String getIncomingTextMethod();
	
	
	void setIncomingTextMethod(String incomingTextMethod);
	
	
	String getIncomingTextFallbackUrl();
	
	
	void setIncomingTextFallbackUrl(String incomingTextFallbackUrl);
	
	
	String getIncomingTextFallbackMethod();
	
	
	void setIncomingTextFallbackMethod(String incomingTextFallbackMethod);
	
	
	String getVoiceForwardingNumber();
	
	
	void setVoiceForwardingNumber(String voiceForwardingNumber);
	
	
	Capabilities getCapabilities();
	
	
	void setCapabilities(Capabilities capabilities);
	
	
	boolean isActive();
	
	
	void setActive(boolean active);
}