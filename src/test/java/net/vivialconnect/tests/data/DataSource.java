package net.vivialconnect.tests.data;

import java.util.List;
import java.util.Map;
import java.util.Date;

import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.account.Contact;
import net.vivialconnect.model.user.User;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.message.Message;
import net.vivialconnect.model.message.Attachment;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;
import net.vivialconnect.model.number.NumberInfo;
import net.vivialconnect.model.log.Log;
import net.vivialconnect.model.log.LogCollection;

public interface DataSource {

    // Account

    Account getAccount() throws VivialConnectException;

    void updateAccount(Account account) throws VivialConnectException;

    // Number

    AssociatedNumber getNumberById(int numberId) throws VivialConnectException;

    AssociatedNumber getLocalNumberById(int numberId) throws VivialConnectException;

    List<AssociatedNumber> getAssociatedNumbers() throws VivialConnectException;

    List<AssociatedNumber> getLocalAssociatedNumbers() throws VivialConnectException;

    List<AvailableNumber> findAvailableNumbersInRegion(String region, Map<String, String> filters) throws VivialConnectException;

    List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException;

    List<AvailableNumber> findAvailableNumbersByPostalCode(String postalCode, Map<String, String> filters) throws VivialConnectException;

    int numberCount() throws VivialConnectException;

    int numberCountLocal() throws VivialConnectException;

    AssociatedNumber buy(String phoneNumber, String areaCode, String phoneNumberType, Map<String, Object> optionalParams) throws VivialConnectException;

    AssociatedNumber buyLocalNumber(String phoneNumber, String areaCode, Map<String, Object> optionalParams) throws VivialConnectException;

    void deleteLocalNumber(AssociatedNumber localNumber) throws VivialConnectException;

    void updateNumber(AssociatedNumber number) throws VivialConnectException;

    void updateLocalNumber(AssociatedNumber number) throws VivialConnectException;

    NumberInfo numberLookup(AssociatedNumber number) throws VivialConnectException;

    // Message

    List<Message> getMessages(Map<String, String> filters) throws VivialConnectException;

    int messageCount() throws VivialConnectException;

    Message getMessageById(int messageId) throws VivialConnectException;

    void sendMessage(Message message) throws VivialConnectException;

    void redactMessage(Message message) throws VivialConnectException;

    List<Attachment> getAttachments(Message message) throws VivialConnectException;

    // Attachment

    Attachment getAttachmentById(int messageId, int attachmentId) throws VivialConnectException;

    int attachmentCount(int messageId) throws VivialConnectException;

    boolean deleteAttachment(Attachment attachment) throws VivialConnectException;

    //Contact

    Contact createContact(Contact contact) throws VivialConnectException;

    Contact updateContact(Contact contact) throws VivialConnectException;

    boolean deleteContact(Contact contact) throws VivialConnectException;

    List<Contact> getContacts() throws VivialConnectException;

    Contact getContactById(int contactId) throws VivialConnectException;

    int contactCount() throws VivialConnectException;

    //User

    User createUser(Map<String, Object> attributes) throws VivialConnectException;

    boolean deleteUser(User user) throws VivialConnectException;

    boolean updateUserPassword(User user, String oldPassword, String newPassword) throws VivialConnectException;

    List<User> getUsers() throws VivialConnectException;

    User getUserById(int userId) throws VivialConnectException;

    int userCount() throws VivialConnectException;

    // Logs

    LogCollection getLogs(Date startTime, Date endTime) throws VivialConnectException;

    LogCollection getAggregate(Date startTime, Date endTime, String aggregatorType) throws VivialConnectException;
}