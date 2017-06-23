package com.vivialconnect.util;

public class JsonBodyBuilder
{

	private String			className;
	private StringBuilder	builder;


	private JsonBodyBuilder(String className)
	{
		this.className = className;
		
		this.builder = new StringBuilder();
		this.builder.append("{\"");
		this.builder.append(translateClassName());
		this.builder.append("\":{");
	}


	public static JsonBodyBuilder forClass(Class<?> clazz)
	{
		return new JsonBodyBuilder(ReflectionUtils.className(clazz));
	}


	public JsonBodyBuilder addParamPair(String name, Object value)
	{
		ValueFormatter formatter = new ValueFormatter(value);
		
		this.builder.append("\"");
		this.builder.append(name);
		this.builder.append("\":");
		this.builder.append(formatter.formatValue());
		this.builder.append(",");
		
		return this;
	}


	public String build()
	{
		removeTrailingComma();
		closeJsonObject();
		
		return this.builder.toString();
	}


	private void closeJsonObject()
	{
		this.builder.append("}}");
	}


	private void removeTrailingComma()
	{
		this.builder.deleteCharAt(this.builder.length() - 1);
	}


	private String translateClassName()
	{
		return separateCamelCase(className, "_").toLowerCase();
	}


	private String separateCamelCase(String name, String separator)
	{
		StringBuilder translation = new StringBuilder();
		for (int i = 0; i < name.length(); i++)
		{
			char character = name.charAt(i);
			if (Character.isUpperCase(character) && translation.length() != 0)
			{
				translation.append(separator);
			}
			
			translation.append(character);
		}
		
		return translation.toString();
	}
	
	class ValueFormatter
	{
		private Object value;
		

		public ValueFormatter(Object value)
		{
			this.value = value;
		}
		
		
		String formatValue()
		{
			StringBuilder builder = new StringBuilder();
			builder.append("\"");
			builder.append(value);
			builder.append("\"");
			
			if (isNumber())
			{
				removeQuotes(builder);
			}
			
			return builder.toString();
		}


		private void removeQuotes(StringBuilder builder)
		{
			builder.deleteCharAt(0);
			builder.deleteCharAt(builder.length() - 1);
		}
		
		
		private boolean isNumber()
		{
			return this.value instanceof Number;
		}
	}
}