package com.vivialconnect.model.number;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.vivialconnect.model.VivialConnectResource;
import com.vivialconnect.model.format.JsonBodyBuilder;

@JsonRootName(value = "phone_number")
public class Number extends VivialConnectResource implements AssociatedNumber, AvailableNumber
{
	
	private static final long serialVersionUID = -1224802858893763457L;
	
	/** Unique identifier of the phone number object */
	@JsonProperty
	private int id;

	/** Creation date (UTC) of the phone number in ISO 8601 format */
	@JsonProperty("date_created")
	private Date dateCreated;

	/** Last modification date (UTC) of phone number in ISO 8601 format */
	@JsonProperty("date_modified")
	private Date dateModified;

	/**
	 * Unique identifier of the account or subaccount associated with the phone number
	 */
	@JsonProperty("account_id")
	private int accountId;
	
	/**
	 * Associated phone number as it is displayed to users. Default format: Friendly national format: (xxx) yyy-zzzz
	 */
	@JsonProperty
	private String name;
	
	/**
	 * Associated phone number in E.164 format (+country code +phone number). For US numbers, the format will be +1xxxyyyzzzz
	 */
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	/**
	 * Type of associated phone number. Possible values: local (non-toll-free) or tollfree
	 */
	@JsonProperty("phone_number_type")
	private String phoneNumberType;
	
	/**
	 * URL to receive message status callback requests for messages sent via the API using this associated phone number
	 */
	@JsonProperty("status_text_url")
	private String statusTextUrl;
	
	/**
	 * URL for receiving SMS messages to the associated phone number. Max. length: 256 characters
	 */
	@JsonProperty("incoming_text_url")
	private String incomingTextUrl;
	
	/**
	 * HTTP method used for the incoming_text_url requests. Max. length: 8 characters. Possible values: GET or POST
	 */
	@JsonProperty("incoming_text_method")
	private String incomingTextMethod;
	
	/**
	 * URL for receiving SMS messages if incoming_text_url fails. Only valid if you provide a value for the incoming_text_url parameter. Max. length: 256 characters
	 */
	@JsonProperty("incoming_text_fallback_url")
	private String incomingTextFallbackUrl;
	
	/**
	 * HTTP method used for incoming_text_fallback_url requests. Max. length: 8 characters. Possible values: GET or POST
	 */
	@JsonProperty("incoming_text_fallback_method")
	private String incomingTextFallbackMethod;
	
	/**
	 * Number to which voice calls will be forwarded
	 */
	@JsonProperty("voice_forwarding_number")
	private String voiceForwardingNumber;
	
	/**
	 * Set of boolean flags indicating the following capabilities supported by the associated phone number
	 */
	@JsonProperty
	private Capabilities capabilities;
	
	/**
	 * City where the available phone number is located
	 */
	@JsonProperty
	private String city;
	
	/**
	 * Two-letter US state abbreviation where the available phone number is located
	 */
	@JsonProperty
	private String region;
	
	/**
	 * Local address and transport area (LATA) where the available phone number is located
	 */
	@JsonProperty
	private String lata;
	
	/**
	 * LATA rate center where the available phone number is located. Usually the same as city
	 */
	@JsonProperty("rate_center")
	private String rateCenter;
	
	@JsonProperty
	private boolean active;
	
	static {
		classesWithoutRootValue.add(NumberCollection.class);
	}
	
	@Override
	public AssociatedNumber buy()
	{
		JsonBodyBuilder builder = JsonBodyBuilder.withCustomClassName("phone_number")
												 .addParamPair("phone_number", getPhoneNumber())
												 .addParamPair("phone_number_type", getPhoneNumberType());
		
		return request(RequestMethod.POST, classURL(Number.class), builder.build(), null, Number.class);
	}
	
	
	public static List<AvailableNumber> findAvailableNumbersInRegion(String region)
	{
		return findAvailableNumbersInRegion(region, null);
	}
	
	
	public static List<AvailableNumber> findAvailableNumbersInRegion(String region, Map<String, String> queryParams)
	{
		return request(RequestMethod.GET, classURLWithSuffix(Number.class, "available/US/local"), null, addQueryParam("region", region, queryParams), NumberCollection.class).getAvailableNumbers();
	}
	
	
	public static List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode)
	{
		return findAvailableNumbersByAreaCode(areaCode, null);
	}
	
	
	public static List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> queryParams)
	{
		return request(RequestMethod.GET, classURLWithSuffix(Number.class, "available/US/local"), null, addQueryParam("area_code", areaCode, queryParams), NumberCollection.class).getAvailableNumbers();
	}
	
	
	public static List<AvailableNumber> findAvailableNumbersByPostalCode(String postalCode)
	{
		return findAvailableNumbersByPostalCode(postalCode);
	}
	
	
	public static List<AvailableNumber> findAvailableNumbersByPostalCode(String postalCode, Map<String, String> queryParams)
	{
		return request(RequestMethod.GET, classURLWithSuffix(Number.class, "available/US/local"), null, addQueryParam("postal_code", postalCode, queryParams), NumberCollection.class).getAvailableNumbers();
	}
	
	
	private static Map<String, String> addQueryParam(String key, String value, Map<String, String> queryParams)
	{
		if (queryParams != null)
		{
			queryParams.put(key, value);
		}
		
		return queryParams;
	}

	
	@Override
	public int getId()
	{
		return id;
	}

	
	@Override
	public void setId(int id)
	{
		this.id = id;
	}

	
	@Override
	public Date getDateCreated()
	{
		return dateCreated;
	}

	
	@Override
	public void setDateCreated(Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	
	@Override
	public Date getDateModified()
	{
		return dateModified;
	}

	
	@Override
	public void setDateModified(Date dateModified)
	{
		this.dateModified = dateModified;
	}

	
	@Override
	public int getAccountId()
	{
		return accountId;
	}

	
	@Override
	public void setAccountId(int accountId)
	{
		this.accountId = accountId;
	}

	
	@Override
	public String getName()
	{
		return name;
	}

	
	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	
	@Override
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	
	@Override
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	
	@Override
	public String getPhoneNumberType()
	{
		return phoneNumberType;
	}
	
	
	@Override
	public void setPhoneNumberType(String phoneNumberType)
	{
		this.phoneNumberType = phoneNumberType;
	}

	
	@Override
	public String getStatusTextUrl()
	{
		return statusTextUrl;
	}

	
	@Override
	public void setStatusTextUrl(String statusTextUrl)
	{
		this.statusTextUrl = statusTextUrl;
	}

	
	@Override
	public String getIncomingTextUrl()
	{
		return incomingTextUrl;
	}

	
	@Override
	public void setIncomingTextUrl(String incomingTextUrl)
	{
		this.incomingTextUrl = incomingTextUrl;
	}

	
	@Override
	public String getIncomingTextMethod()
	{
		return incomingTextMethod;
	}

	
	@Override
	public void setIncomingTextMethod(String incomingTextMethod)
	{
		this.incomingTextMethod = incomingTextMethod;
	}

	
	@Override
	public String getIncomingTextFallbackUrl()
	{
		return incomingTextFallbackUrl;
	}
	
	
	@Override
	public void setIncomingTextFallbackUrl(String incomingTextFallbackUrl)
	{
		this.incomingTextFallbackUrl = incomingTextFallbackUrl;
	}

	
	@Override
	public String getIncomingTextFallbackMethod()
	{
		return incomingTextFallbackMethod;
	}

	
	@Override
	public void setIncomingTextFallbackMethod(String incomingTextFallbackMethod)
	{
		this.incomingTextFallbackMethod = incomingTextFallbackMethod;
	}

	
	@Override
	public String getVoiceForwardingNumber()
	{
		return voiceForwardingNumber;
	}

	
	@Override
	public void setVoiceForwardingNumber(String voiceForwardingNumber)
	{
		this.voiceForwardingNumber = voiceForwardingNumber;
	}
	
	
	@Override
	public Capabilities getCapabilities()
	{
		return capabilities;
	}
	
	
	@Override
	public void setCapabilities(Capabilities capabilities)
	{
		this.capabilities = capabilities;
	}

	
	@Override
	public String getCity()
	{
		return city;
	}

	
	@Override
	public void setCity(String city)
	{
		this.city = city;
	}

	
	@Override
	public String getRegion()
	{
		return region;
	}
	
	
	@Override
	public void setRegion(String region)
	{
		this.region = region;
	}

	
	@Override
	public String getLata()
	{
		return lata;
	}

	
	@Override
	public void setLata(String lata)
	{
		this.lata = lata;
	}

	
	@Override
	public String getRateCenter()
	{
		return rateCenter;
	}

	
	@Override
	public void setRateCenter(String rateCenter)
	{
		this.rateCenter = rateCenter;
	}
	
	
	@Override
	public boolean isActive()
	{
		return active;
	}
	
	
	@Override
	public void setActive(boolean active)
	{
		this.active = active;
	}
}