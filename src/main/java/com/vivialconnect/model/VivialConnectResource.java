package com.vivialconnect.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SimpleTimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivialconnect.client.VivialConnectClient;
import com.vivialconnect.http.CanonicalRequestBuilder;
import com.vivialconnect.http.RequestBuilder;
import com.vivialconnect.http.VivialRESTClient;
import com.vivialconnect.model.account.Account;
import com.vivialconnect.model.error.ErrorMessage;
import com.vivialconnect.model.error.NoContentException;
import com.vivialconnect.model.error.VivialConnectException;
import com.vivialconnect.model.format.JsonBodyBuilder;
import com.vivialconnect.util.CryptoUtils;
import com.vivialconnect.util.ReflectionUtils;

public abstract class VivialConnectResource implements Serializable
{
	
	private static final long serialVersionUID = -2726239361148612818L;
	
	private static final String SIGNATURE_ALGORITHM = "HmacSHA256";

	protected static final String	ISO_8601_FORMAT		= "yyyyMMdd'T'HHmmss'Z'";
	protected static final String	HTTP_DATE_FORMAT	= "E, dd MMM yyyy HH:mm:ss z";
	
	protected static Set<Class<?>> classesWithoutRootValue = new HashSet<Class<?>>();
	static {
		classesWithoutRootValue.add(ResourceCount.class);
	}
	
	protected enum RequestMethod
	{
		GET, POST, PUT, DELETE
	}
	
	
	protected static String classURLWithSuffix(Class<?> clazz, String suffix)
	{
		return String.format("%ss/%s.json", singleClassURL(clazz), suffix);
	}
	

	protected static String classURL(Class<?> clazz)
	{
		return String.format("%ss.json", singleClassURL(clazz));
	}
	
	
	protected static String singleClassURL(Class<?> clazz)
	{
		if (Account.class.equals(clazz))
		{
			return String.format("%s/accounts/%d.json", VivialConnectClient.API_BASE, VivialConnectClient.getAccountId());
		}
		
		return String.format("%s/accounts/%d/%s", VivialConnectClient.API_BASE,
												  VivialConnectClient.getAccountId(),
												  ReflectionUtils.className(clazz).toLowerCase());
	}
	
	
	protected static String unmappedURL(String resourceName)
	{
		return String.format("%s.json", formatURLForResource(resourceName));
	}
	
	
	protected static String formatURLForResource(String resourceName)
	{
		return String.format("%s/accounts/%d/%s", VivialConnectClient.API_BASE,
												  VivialConnectClient.getAccountId(),
												  resourceName);
	}
	
	
	protected static <T> T request(VivialConnectResource.RequestMethod method,
								   String url, String body, Map<String, String> queryParams,
								   Class<T> responseClass) throws VivialConnectException
	{
		try
		{
			URL endpoint = createEndpoint(url, method, queryParams);
			Date currentDate = new Date();
			
			String requestTimestamp = createRequestTimestamp(currentDate);
			String requestDate = createRequestDate(currentDate);
			
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Date", requestDate);
			headers.put("Host", endpoint.getHost());
			headers.put("Accept", "application/json");
			
			if (requestSupportsBody(method.name()))
			{
				headers.put("Content-Type", "application/json");
			}
			
			CanonicalRequestBuilder canonicalRequestbuilder = new CanonicalRequestBuilder();
			canonicalRequestbuilder.endpoint(endpoint)
								   .requestTimestamp(requestTimestamp)
								   .body(body)
								   .method(method.name())
								   .headers(headers)
								   .queryParams(queryParams);
			
			String canonicalRequest = canonicalRequestbuilder.build();
			String signedHeaders = canonicalRequestbuilder.getCanonicalizedHeaderNames();
			String signature = createSignature(canonicalRequest);
			
			String authoritzationHeader = buildAuthoritzationHeader(signature);
			headers.put("Authorization", authoritzationHeader);
			headers.put("X-Auth-Date", requestTimestamp);
			headers.put("X-Auth-SignedHeaders", signedHeaders);
			
			return request(endpoint, method, headers, queryParams, body, responseClass);
			//return jerseyRequest(endpoint, method, headers, queryParams, body, responseClass);
		}
		catch (NoContentException nce)
		{
			throw nce;
		}
		catch (Exception e)
		{
			throw handleException(e);
		}
	}


	private static VivialConnectException handleException(Exception e)
	{
		VivialConnectException vce = null;
		
		if (VivialConnectException.class.isAssignableFrom(e.getClass()))
		{
			vce = (VivialConnectException) e;
		}
		else
		{
			vce = new VivialConnectException(e);
		}
		
		return vce;
	}


	private static boolean requestSupportsBody(String method)
	{
		String[] supportedMethods = { "DELETE", "POST", "PUT" };
		return Arrays.binarySearch(supportedMethods, method) > -1;
	}


	private static <T> T jerseyRequest(URL endpoint, RequestMethod method, Map<String, String> headers,
			Map<String, String> queryParams, String body, Class<T> clazz)
	{
		VivialRESTClient client = new VivialRESTClient(VivialConnectClient.API_BASE);
		RequestBuilder builder = client.request().path("accounts/10128/messages.json");
		
		for (String headerName : headers.keySet())
		{
			String headerValue = headers.get(headerName);
			builder.header(headerName, headerValue);
		}
		
		try
		{
			String response = "null";
			
			switch(method) {
				case GET:
					if (queryParams != null)
					{
						for (String key : queryParams.keySet())
						{
							String value = queryParams.get(key);
							builder.queryParam(key, value);
						}
					}
					
					response = builder.get(String.class);
					break;
				case POST:
					response = builder.post(String.class, body);
					break;
				default:
					break;
			}
			
			System.out.println(response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}


	private static URL createEndpoint(String url, RequestMethod method,
								      Map<String, String> queryParams) throws MalformedURLException
	{
		if (method == RequestMethod.GET && queryParams != null && !queryParams.isEmpty())
		{
			StringBuilder urlBuilder = new StringBuilder(url).append("?");
			for (String key : queryParams.keySet())
			{
				String value = queryParams.get(key);
				
				urlBuilder.append(key).append("=").append(value).append("&");
			}
			
			url = urlBuilder.deleteCharAt(urlBuilder.length() - 1).toString();
		}
		
		return new URL(url);
	}
	
	
	protected static String createRequestTimestamp(Date currentDate)
	{
		DateFormat iso8601 = new SimpleDateFormat(ISO_8601_FORMAT);
		iso8601.setTimeZone(new SimpleTimeZone(0, "GMT"));
		
		return iso8601.format(currentDate);
	}
	
	
	private static String createRequestDate(Date currentDate)
	{
		DateFormat requestDateFormat = new SimpleDateFormat(HTTP_DATE_FORMAT);
		requestDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
		
		return requestDateFormat.format(currentDate);
	}
	
	
	private static String createSignature(String canonicalRequest)
	{
		try
		{
			Mac hmac = Mac.getInstance(SIGNATURE_ALGORITHM);
			SecretKeySpec secretKey = new SecretKeySpec(VivialConnectClient.getApiSecret().getBytes(),
									              		SIGNATURE_ALGORITHM);
			hmac.init(secretKey);
			
			byte[] signatureBytes = hmac.doFinal(canonicalRequest.getBytes("UTF-8"));
			return CryptoUtils.toHex(signatureBytes);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	private static String buildAuthoritzationHeader(String signature)
	{
		return new StringBuilder().append("HMAC ")
								  .append(VivialConnectClient.getApiKey())
								  .append(":")
								  .append(signature)
								  .toString();
	}


	private static <T> T request(URL endpoint, VivialConnectResource.RequestMethod method, Map<String, String> headers,
								 Map<String, String> queryParams, String body, Class<T> responseClass) throws IOException, NoContentException, VivialConnectException
	{
		HttpURLConnection connection = null;
		
		try
		{
			connection = prepareConnection(endpoint, method);
			setHeaders(connection, headers);
			setBody(connection, body);
			
			String response = doRequest(connection);
			System.out.println(response);
			
			return unmarshallResponse(response, responseClass);
		}
		finally
		{
			disconnect(connection);
		}
	}
	
	
	private static HttpURLConnection prepareConnection(URL endpoint, RequestMethod method) throws IOException
	{
		HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
		connection.setRequestMethod(method.name());
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
		return connection;
	}
	
	
	private static void setHeaders(HttpURLConnection connection, Map<String, String> headers)
	{
		for (String headerName : headers.keySet())
		{
			String headerValue = headers.get(headerName);
			connection.setRequestProperty(headerName, headerValue);
		}
	}

	
	private static void setBody(HttpURLConnection connection, String body) throws IOException
	{
		if (requestSupportsBody(connection.getRequestMethod()) && body != null && !body.isEmpty())
		{
			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.writeBytes(body);
			outputStream.flush();
			outputStream.close();
		}
	}

	
	private static String doRequest(HttpURLConnection connection) throws NoContentException, VivialConnectException
	{
		BufferedReader reader = null;
		
		try
		{
			InputStream inputStream = connection.getInputStream();
			reader = createBufferedReader(inputStream);
			
			String response = readResponse(reader);
			if (connection.getResponseCode() == 204 /* No Content */)
			{
				throw new NoContentException();
			}
			
			return response;
		}
		catch(IOException ioe)
		{
			throw convertToVivialException(ioe, connection);
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					throw convertToVivialException(e, null);
				}
			}
		}
	}
	

	private static VivialConnectException convertToVivialException(IOException ioe, HttpURLConnection connection)
	{
		if (connection == null)
		{
			return new VivialConnectException(ioe);
		}
		
		BufferedReader reader = null;
		
		try
		{
			reader = createBufferedReader(connection.getErrorStream());
			String errorResponse = readResponse(reader);
			String errorMessage = unmarshalErrorResponse(errorResponse);
			
			VivialConnectException vivialException = new VivialConnectException(errorMessage, ioe);
			vivialException.setResponseCode(connection.getResponseCode());
			
			return vivialException;
		}
		catch (IOException e)
		{
			return new VivialConnectException(e);
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					return new VivialConnectException(e);
				}
			}
		}
	}


	private static String unmarshalErrorResponse(String errorResponse)
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Object unmarshalledObject = mapper.reader()
											  .forType(ErrorMessage.class)
											  .readValue(errorResponse);
			
			ErrorMessage errorMessage = (ErrorMessage) unmarshalledObject;
			return errorMessage.getErrorMessage();
		}
		catch (Exception e)
		{
			return errorResponse;
		}
	}


	private static BufferedReader createBufferedReader(InputStream inputStream) throws IOException
	{
		return new BufferedReader(new InputStreamReader(inputStream));
	}


	private static String readResponse(BufferedReader reader) throws IOException
	{
		StringBuilder responseBuilder = new StringBuilder();
		String line = null;
		
		while((line = reader.readLine()) != null)
		{
			responseBuilder.append(line).append("\n");
		}
		
		return responseBuilder.toString();
	}
	
	
	private static <T> T unmarshallResponse(String response, Class<T> responseClass) throws JsonProcessingException, IOException
	{
		ObjectMapper mapper = configureObjectMapper(responseClass);
		return mapper.reader().forType(responseClass).readValue(response);
	}


	private static <T> ObjectMapper configureObjectMapper(Class<T> responseClass)
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		if (shouldUnwrapRoot(responseClass))
		{
			mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		}
		
		return mapper;
	}
	
	
	private static boolean shouldUnwrapRoot(Class<?> responseClass)
	{
		return !classesWithoutRootValue.contains(responseClass);
	}


	private static void disconnect(HttpURLConnection connection)
	{
		if (connection != null)
		{
			connection.disconnect();
		}
	}
	
	
	protected static Map<String, String> addQueryParam(String key, String value, Map<String, String> queryParams)
	{
		if (queryParams != null)
		{
			queryParams.put(key, value);
		}
		
		return queryParams;
	}


	protected static void ifParamValidAddToBuilder(JsonBodyBuilder builder, String paramName, String value)
	{
		if (value != null && !value.isEmpty())
		{
			builder.addParamPair(paramName, value);
		}
	}
	
	
	protected static void ifParamValidAddToBuilder(JsonBodyBuilder builder, String paramName, int intValue)
	{
		if (intValue > 0)
		{
			builder.addParamPair(paramName, intValue);
		}
	}
}