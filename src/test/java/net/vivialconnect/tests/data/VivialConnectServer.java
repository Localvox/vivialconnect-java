package net.vivialconnect.tests.data;

import java.util.List;
import java.util.Map;

import net.vivialconnect.client.VivialConnectClient;
import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.message.Message;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;
import net.vivialconnect.model.number.Number;
import net.vivialconnect.model.number.NumberInfo;

public class VivialConnectServer implements DataSource {

    protected VivialConnectServer() {
        initClient();
    }

    private void initClient() {
        String accountId = System.getProperty("vivialconnect.test.account-id");
        String apiKey = System.getProperty("vivialconnect.test.api-key");
        String apiSecret = System.getProperty("vivialconnect.test.api-secret");

        VivialConnectClient.init(Integer.parseInt(accountId), apiKey, apiSecret);

        String baseUrl = System.getProperty("vivialconnect.test.base-url");
        if (baseUrl != null && !baseUrl.isEmpty()) {
            VivialConnectClient.overrideApiBaseUrl(baseUrl);
        }
    }

    @Override
    public Account getAccount() throws VivialConnectException {
        return Account.getAccount();
    }

    @Override
    public void updateAccount(Account account) throws VivialConnectException {
        account.update();
    }

    @Override
    public AssociatedNumber getNumberById(int numberId) throws VivialConnectException {
        return Number.getNumberById(numberId);
    }

    @Override
    public List<AssociatedNumber> getAssociatedNumbers() throws VivialConnectException {
        return Number.getAssociatedNumbers();
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException {
        return Number.findAvailableNumbersByAreaCode(areaCode, filters);
    }
    
    @Override
    public int numberCount() throws VivialConnectException {
    	return Number.count();
    }
    
    @Override
    public List<AssociatedNumber> getLocalAssociatedNumbers() throws VivialConnectException {
    	return Number.getLocalAssociatedNumbers();
    }
    
    @Override
    public void deleteLocalNumber(AssociatedNumber localNumber) throws VivialConnectException {
    	localNumber.deleteLocalNumber();
    }
    
    @Override
    public void updateNumber(AssociatedNumber number) throws VivialConnectException {
    	number.update();
    }
    
    @Override
    public NumberInfo numberLookup(AssociatedNumber number) throws VivialConnectException {
    	return number.lookup();
    }
    
    @Override
    public List<Message> getMessages(Map<String, String> filters) throws VivialConnectException {
    	return Message.getMessages(filters);
    }
    
    @Override
    public Message getMessageById(int messageId) throws VivialConnectException {
    	return Message.getMessageById(messageId);
    }
    
    @Override
    public void sendMessage(Message message) throws VivialConnectException {
    	message.send();
    }
    
    @Override
    public void redactMessage(Message message) throws VivialConnectException {
    	message.redact();
    }
    
    @Override
    public int messageCount() throws VivialConnectException {
    	return Message.count();
    }
}