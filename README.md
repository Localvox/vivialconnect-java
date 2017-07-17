
# VivialConnect Client for Java

VivialConnect is a simple SMS/MSS API. It's designed specifically for developers seeking a simple, affordable and scalable messaging solution.

Get your API key here: https://www.vivialconnect.net/register 
Be sure to read the API documentation: https://www.vivialconnect.net/docs 

### Requirements

* [JDK 6 or latest](http://www.oracle.com/technetwork/java/javase/downloads/index.html)  

### Maven Installation

Include the following dependency into your pom.xml:
```xml
<dependency>
  <groupId>z.y.z</groupId>
  <artifactId>x.y.z</artifactId>
  <version>x.y.z</version>
</dependency>
```
### Manual Installation

You can clone the VivialConnect Java client repository into your project:
```
git clone https://github.com/VivialConnect/vivialconnect-java
```

### Examples

__Initialize Client:__ Needed before attempting to use any resource.

```java
VivialConnectClient.init(ACCOUNT_ID, API_KEY, API_SECRET);
```

__Search for and buying a number:__ 

```java
List<AvailableNumber> availableNumbers = Number.findAvailableNumbersByAreaCode("AREA_CODE");
AvailableNumber availableNumber = availableNumbers.get(0);
AssociatedNumber associatedNumber = availableNumber.buy();
```
__Send a text message:__ 

```java
Message message = new Message();
message.setFromNumber(FROM_NUMBER);
message.setToNumber(TO_NUMBER);
message.setBody("Hello, from Vivial Connect!");
message.send(); 
```
__Retrieve a list of all messages sent:__ 

```java
Message.getMessages();
```
__Get a specific message by ID:__ 

```java
Message message = Message.getMessageById(MSG_ID);
```