package com.vivialconnect.model.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.vivialconnect.model.ResourceCount;
import com.vivialconnect.model.VivialConnectResource;
import com.vivialconnect.model.format.JsonBodyBuilder;
import com.vivialconnect.util.StringUtils;

@JsonRootName(value = "message")
public class Message extends VivialConnectResource
{
	
	private static final long serialVersionUID = 5181807107956389186L;

	/** Unique identifier of the text message object */
	@JsonProperty
	private int id;

	/** Creation date (UTC) of the text message in ISO 8601 format */
	@JsonProperty("date_created")
	private Date dateCreated;

	/** Last modification date (UTC) of the text message in ISO 8601 format */
	@JsonProperty("date_modified")
	private Date dateModified;

	/**
	 * Unique identifier of the account or subaccount associated with the text
	 * message
	 */
	@JsonProperty("account_id")
	private int accountId;

	/**
	 * For subaccounts, the account_id of the subaccount’s parent (primary)
	 * account
	 */
	@JsonProperty("master_account_id")
	private int masterAccountId;

	/**
	 * String identifying the type of inbound or outbound text message. Possible
	 * values: local_sms, tollfree_sms, or local_mms
	 */
	@JsonProperty("message_type")
	private String messageType;

	/**
	 * Inbound/outbound direction of the text message, and if outbound, the
	 * nature of the text message initiation
	 */
	@JsonProperty
	private String direction;

	/**
	 * Phone number that received the text message. Uses E.164 format (+country
	 * code +phone number). For US, the format will be +1xxxyyyzzzz
	 */
	@JsonProperty("to_number")
	private String toNumber;

	/**
	 * One of the following:
	 * 
	 * For inbound messages, the external phone number that sent the text
	 * message. Uses E.164 format (+country code +phone number). For US, the
	 * format will be +1xxxyyyzzzz
	 * 
	 * For outbound messages, the associated phone number in your account that
	 * sent the text message
	 * 
	 */
	@JsonProperty("from_number")
	private String fromNumber;

	/** The id of the Connector to use to send the message with */
	@JsonProperty("connector_id")
	private int connectorId;

	/**
	 * One of the following in ISO 8601 format:
	 * 
	 * For inbound messages, the UTC timestamp the text message was received
	 * 
	 * For outbound messages, the UTC timestamp the text message was sent
	 * 
	 */
	@JsonProperty
	private Date sent;

	/** Number of media attachments for the text message */
	@JsonProperty("num_media")
	private int numMedia;

	/** Number of segments that make up the message */
	@JsonProperty("num_segments")
	private int numSegments;

	/** Text body of the text message. Max. length: 1,600 characters */
	@JsonProperty
	private String body;

	/** Status of the message */
	@JsonProperty
	private String status;

	/** Error code, if any, for the message */
	@JsonProperty("error_code")
	private String errorCode;

	/** Error code message for error_code as it is displayed to users */
	@JsonProperty("error_message")
	private String errorMessage;

	/**
	 * Amount billed for the message, in the currency associated with the
	 * account
	 */
	@JsonProperty
	private int price;

	/**
	 * Currency in which price is measured in ISO 4127 format. For US, the
	 * currency will be USD
	 */
	@JsonProperty("price_currency")
	private String priceCurrency;
	
	/**
	 * URLs of the media you wish to send out with the message.
	 */
	private List<String> mediaUrls;
	
	static {
		classesWithoutRootValue.add(MessageCollection.class);
		classesWithoutRootValue.add(AttachmentCollection.class);
	}
	
	
	public Message send()
	{
		return request(RequestMethod.POST, classURL(Message.class), jsonBody(), null, Message.class);
	}
	
	
	private String jsonBody()
	{
		JsonBodyBuilder builder = JsonBodyBuilder.forClass(Message.class);
		if (hasMediaUrls())
		{
			builder.addParamPair("media_urls", mediaUrls);
		}
		
		if (connectorId > 0)
		{
			builder.addParamPair("connector_id", connectorId);
		}
		
		return builder.addParamPair("from_number", this.fromNumber)
					  .addParamPair("to_number", this.toNumber)
					  .addParamPair("body", this.body)
					  .build();
	}


	private boolean hasMediaUrls()
	{
		return this.mediaUrls != null && !this.mediaUrls.isEmpty();
	}
	
	
	public static Message getMessageById(Integer messageId)
	{
		return request(RequestMethod.GET, classURLWithSuffix(Message.class, String.valueOf(messageId)), null, null, Message.class);
	}
	
	
	public static List<Message> getMessages()
	{
		return getMessages(null);
	}
	
	
	public static List<Message> getMessages(Map<String, String> queryParameters)
	{
		return request(RequestMethod.GET, classURL(Message.class), null, queryParameters, MessageCollection.class).getMessages();
	}
	
	
	public static int count()
	{
		return request(RequestMethod.GET, classURLWithSuffix(Message.class, "count"), null, null, ResourceCount.class).getCount();
	}
	
	
	public List<Attachment> getAttachments()
	{
		return request(RequestMethod.GET, classURLWithSuffix(Message.class, String.format("%d/attachments", this.getId())), null, null, AttachmentCollection.class).getAttachments();
	}
	
	
	public Message redact()
	{
		return request(RequestMethod.PUT, classURLWithSuffix(Message.class, String.valueOf(this.getId())), jsonBodyEmpty(), null, Message.class);
	}
	
	
	private String jsonBodyEmpty()
	{
		return JsonBodyBuilder.forClass(Message.class)
						 	  .addParamPair("id", getId())
						      .addParamPair("body", "")
						      .build();
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


	public int getMasterAccountId()
	{
		return masterAccountId;
	}


	public void setMasterAccountId(int masterAccountId)
	{
		this.masterAccountId = masterAccountId;
	}


	public String getMessageType()
	{
		return messageType;
	}


	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}


	public String getDirection()
	{
		return direction;
	}


	public void setDirection(String direction)
	{
		this.direction = direction;
	}


	public String getToNumber()
	{
		return toNumber;
	}


	public void setToNumber(String toNumber)
	{
		this.toNumber = toNumber;
	}


	public String getFromNumber()
	{
		return fromNumber;
	}


	public void setFromNumber(String fromNumber)
	{
		this.fromNumber = fromNumber;
	}


	public int getConnectorId()
	{
		return connectorId;
	}


	public void setConnectorId(int connectorId)
	{
		this.connectorId = connectorId;
	}


	public Date getSent()
	{
		return sent;
	}


	public void setSent(Date sent)
	{
		this.sent = sent;
	}


	public int getNumMedia()
	{
		return numMedia;
	}


	public void setNumMedia(int numMedia)
	{
		this.numMedia = numMedia;
	}


	public int getNumSegments()
	{
		return numSegments;
	}


	public void setNumSegments(int numSegments)
	{
		this.numSegments = numSegments;
	}


	public String getBody()
	{
		return body;
	}


	public void setBody(String body)
	{
		/* Escape Non-ASCII characters in order to support things like emojis and Chinese characters */
		this.body = StringUtils.escapeNonAsciiCharacters(body);
	}


	public String getStatus()
	{
		return status;
	}


	public void setStatus(String status)
	{
		this.status = status;
	}


	public String getErrorCode()
	{
		return errorCode;
	}


	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}


	public String getErrorMessage()
	{
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}


	public int getPrice()
	{
		return price;
	}


	public void setPrice(int price)
	{
		this.price = price;
	}


	public String getPriceCurrency()
	{
		return priceCurrency;
	}


	public void setPriceCurrency(String priceCurrency)
	{
		this.priceCurrency = priceCurrency;
	}
	
	
	public Message addMediaUrl(String mediaUrl)
	{
		if (mediaUrls == null)
		{
			mediaUrls = new ArrayList<String>();
		}
		
		mediaUrls.add(mediaUrl);
		return this;
	}
	
	
	public List<String> getMediaUrls()
	{
		return mediaUrls;
	}
	
	
	public void setMediaUrls(List<String> mediaUrls)
	{
		this.mediaUrls = mediaUrls;
	}
	
	
	@Override
	public String toString()
	{
		return "Message [id=" + id + ", dateCreated=" + dateCreated + ", dateModified=" + dateModified + ", accountId="
				+ accountId + ", masterAccountId=" + masterAccountId + ", messageType=" + messageType + ", direction="
				+ direction + ", toNumber=" + toNumber + ", fromNumber=" + fromNumber + ", connectorId=" + connectorId
				+ ", sent=" + sent + ", numMedia=" + numMedia + ", numSegments=" + numSegments + ", body=" + body
				+ ", status=" + status + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", price="
				+ price + ", priceCurrency=" + priceCurrency + "]";
	}
}