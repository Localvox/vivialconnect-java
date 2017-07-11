package com.vivialconnect.model.account;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivialconnect.model.VivialConnectResource;

public class Contact extends VivialConnectResource
{

	private static final long serialVersionUID = 3140451099385557777L;
	
	/** Unique identifier of the user object */
	@JsonProperty
	private int id;
	
	/** Creation date (UTC) of the contact in ISO 8601 format */
	@JsonProperty("date_created")
	private Date dateCreated;

	/** Last modification date (UTC) of contact in ISO 8601 format */
	@JsonProperty("date_modified")
	private Date dateModified;
	
	/**
	 * Unique identifier of the parent account
	 */
	@JsonProperty("account_id")
	private int accountId;
	
	@JsonProperty
	private boolean active;
	
	@JsonProperty
	private String address1;
	
	@JsonProperty
	private String address2;
	
	@JsonProperty
	private String address3;
	
	@JsonProperty
	private String city;
	
	/** Name of company */
	@JsonProperty("company_name")
	private String companyName;
	
	/** The type of contact. Can be one of: ‘agency,’ ‘billing,’ ‘main,’ ‘company,’ or ‘marketing.’ */
	@JsonProperty("contact_type")
	private String contactType;
	
	@JsonProperty
	private String country;
	
	/** Contacts's email address */
	@JsonProperty
	private String email;
	
	/** Contacts's fax number */
	@JsonProperty
	private String fax;
	
	/** Contacts's first name */
	@JsonProperty("first_name")
	private String firstName;
	
	/** Contacts's last name */
	@JsonProperty("last_name")
	private String lastName;
	
	/** Contacts's mobile phone number */
	@JsonProperty("mobile_phone")
	private String mobilePhone;
	
	/** Contacts's postal code */
	@JsonProperty("postal_code")
	private String postalCode;
	
	@JsonProperty
	private String state;
	
	/** Contacts's position or title */
	@JsonProperty
	private String title;
	
	/** Contacts's work phone number */
	@JsonProperty("work_phone")
	private String workPhone;
	
	
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

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public String getAddress1()
	{
		return address1;
	}

	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}

	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	public String getAddress3()
	{
		return address3;
	}

	public void setAddress3(String address3)
	{
		this.address3 = address3;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getContactType()
	{
		return contactType;
	}

	public void setContactType(String contactType)
	{
		this.contactType = contactType;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getWorkPhone()
	{
		return workPhone;
	}

	public void setWorkPhone(String workPhone)
	{
		this.workPhone = workPhone;
	}
}