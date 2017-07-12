package com.vivialconnect.model.account;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.vivialconnect.model.VivialConnectResource;
import com.vivialconnect.model.format.JsonBodyBuilder;
import com.vivialconnect.model.log.Log;
import com.vivialconnect.model.log.LogCollection;

@JsonRootName("account")
public class Account extends VivialConnectResource
{

	private static final long serialVersionUID = -2624039897600671223L;
	
	/** Unique identifier of the account object */
	@JsonProperty
	private int id;

	/** Creation date (UTC) of the account in ISO 8601 format */
	@JsonProperty("date_created")
	private Date dateCreated;

	/** Last modification date (UTC) of account in ISO 8601 format */
	@JsonProperty("date_modified")
	private Date dateModified;
	
	/**
	 * Unique identifier of the parent account
	 */
	@JsonProperty("account_id")
	private int accountId;
	
	/** Account name as it is displayed to users (for example, the name of your company) */
	@JsonProperty("company_name")
	private String companyName;
		
	@JsonProperty
	private boolean active;
	
	/** Account's contacts */
	@JsonProperty
	private List<Contact> contacts;
	
	/** Account's services */
	@JsonProperty
	private List<Service> services;
	
	static {
		classesWithoutRootValue.add(LogCollection.class);
		classesWithoutRootValue.add(ContactCollection.class);
	}
	
	
	public static Account getAccount()
	{
		return request(RequestMethod.GET, singleClassURL(Account.class), null, null, Account.class);
	}
	
	
	public static List<Log> getLogs(Date startTime, Date endTime)
	{
		return request(RequestMethod.GET, classURL(Log.class), null, null, LogCollection.class).getLogs();
	}
	
	
	public Account update()
	{
		Account updatedAccount = request(RequestMethod.PUT, singleClassURL(Account.class),
										 buildJsonBodyForUpdate(), null, Account.class);
		updateFields(updatedAccount);
		return this;
	}
	

	private String buildJsonBodyForUpdate()
	{
		JsonBodyBuilder builder = JsonBodyBuilder.forClass(Account.class);
		fillOptionalFieldsForUpdate(builder);
		
		return builder.build();
	}
	
	
	private void fillOptionalFieldsForUpdate(JsonBodyBuilder builder)
	{
		ifParamValidAddToBuilder(builder, "id", getId());
		ifParamValidAddToBuilder(builder, "company_name", getCompanyName());
	}
	
	
	private void updateFields(Account updatedAccount)
	{
		this.id = updatedAccount.getId();
		this.accountId = updatedAccount.getAccountId();
		this.active = updatedAccount.isActive();
		this.companyName = updatedAccount.getCompanyName();
		this.dateCreated = updatedAccount.getDateCreated();
		this.dateModified = updatedAccount.getDateModified();
		this.contacts = updatedAccount.getContacts();
		this.services = updatedAccount.getServices();
	}


	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public Date getDateCreated()
	{
		return dateCreated;
	}


	public void setDateCreated(Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}


	public Date getDateModified()
	{
		return dateModified;
	}


	public void setDateModified(Date dateModified)
	{
		this.dateModified = dateModified;
	}


	public int getAccountId()
	{
		return accountId;
	}


	public void setAccountId(int accountId)
	{
		this.accountId = accountId;
	}


	public String getCompanyName()
	{
		return companyName;
	}


	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}


	public boolean isActive()
	{
		return active;
	}


	public void setActive(boolean active)
	{
		this.active = active;
	}


	public List<Contact> getContacts()
	{
		return contacts;
	}


	public void setContacts(List<Contact> contacts)
	{
		this.contacts = contacts;
	}


	public List<Service> getServices()
	{
		return services;
	}


	public void setServices(List<Service> services)
	{
		this.services = services;
	}
}