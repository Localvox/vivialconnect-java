package net.vivialconnect.tests.data;

import java.util.List;
import java.util.Map;

import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.message.Message;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;
import net.vivialconnect.model.number.NumberInfo;

public interface DataSource {

    Account getAccount() throws VivialConnectException;

    void updateAccount(Account account) throws VivialConnectException;

    AssociatedNumber getNumberById(int numberId) throws VivialConnectException;

    List<AssociatedNumber> getAssociatedNumbers() throws VivialConnectException;

    List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException;

    int numberCount() throws VivialConnectException;

    List<AssociatedNumber> getLocalAssociatedNumbers() throws VivialConnectException;

    void deleteLocalNumber(AssociatedNumber localNumber) throws VivialConnectException;

    void updateNumber(AssociatedNumber number) throws VivialConnectException;

    NumberInfo numberLookup(AssociatedNumber number) throws VivialConnectException;

    List<Message> getMessages(Map<String, String> filters) throws VivialConnectException;

    int messageCount() throws VivialConnectException;

    Message getMessageById(int messageId) throws VivialConnectException;

    void sendMessage(Message message) throws VivialConnectException;

    void redactMessage(Message message) throws VivialConnectException;
}