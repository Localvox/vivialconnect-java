package net.vivialconnect.tests.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.vivialconnect.client.VivialConnectClient;
import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.account.Contact;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.message.Message;
import net.vivialconnect.model.message.Attachment;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;
import net.vivialconnect.model.number.Number;
import net.vivialconnect.model.number.NumberInfo;
import net.vivialconnect.model.user.User;
import net.vivialconnect.model.format.JsonBodyBuilder;
import net.vivialconnect.model.log.Log;
import net.vivialconnect.model.log.LogCollection;

// Test-only subclass of user for creating new users through the API
class AdminUser extends User {
    public static User createUser(Map<String, Object> attributes) throws VivialConnectException {
        JsonBodyBuilder builder = JsonBodyBuilder.withCustomClassName("user");
        if (attributes != null) {
            builder = builder.addParams(attributes);
        }
        return request(RequestMethod.POST, classURL(User.class), builder.build(), null, User.class);
    }
}

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
    public AssociatedNumber getLocalNumberById(int numberId) throws VivialConnectException {
        return Number.getLocalNumberById(numberId);
    }

    @Override
    public List<AssociatedNumber> getAssociatedNumbers() throws VivialConnectException {
        return Number.getAssociatedNumbers();
    }

    @Override
    public List<AssociatedNumber> getLocalAssociatedNumbers() throws VivialConnectException {
        return Number.getLocalAssociatedNumbers();
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersInRegion(String region, Map<String, String> filters) throws VivialConnectException {
        return Number.findAvailableNumbersInRegion(region, filters);
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException {
        return Number.findAvailableNumbersByAreaCode(areaCode, filters);
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersByPostalCode(String postalCode, Map<String, String> filters) throws VivialConnectException {
        return Number.findAvailableNumbersByPostalCode(postalCode, filters);
    }

    @Override
    public int numberCount() throws VivialConnectException {
        return Number.count();
    }

    @Override
    public int numberCountLocal() throws VivialConnectException {
        return Number.countLocal();
    }

    @Override
    public AssociatedNumber buy(String phoneNumber, String areaCode, String phoneNumberType, Map<String, Object> optionalParams) throws VivialConnectException {
        return Number.buy(phoneNumber, areaCode, phoneNumberType, optionalParams);
    }

    @Override
    public AssociatedNumber buyLocalNumber(String phoneNumber, String areaCode, Map<String, Object> optionalParams) throws VivialConnectException {
        return Number.buyLocalNumber(phoneNumber, areaCode, optionalParams);
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
    public void updateLocalNumber(AssociatedNumber number) throws VivialConnectException {
        number.updateLocalNumber();
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

    @Override
    public List<Attachment> getAttachments(Message message) throws VivialConnectException {
        return message.getAttachments();
    }

    @Override
    public Attachment getAttachmentById(int messageId, int attachmentId) throws VivialConnectException{
        return Attachment.getAttachmentById(messageId, attachmentId);
    }

    @Override
    public int attachmentCount(int messageId) throws VivialConnectException{
        return Attachment.count(messageId);
    }

    @Override
    public boolean deleteAttachment(Attachment attachment) throws VivialConnectException{
        return attachment.delete();
    }

    @Override
    public Contact createContact(Contact contact) throws VivialConnectException {
        return contact.create();
    }

    @Override
    public Contact updateContact(Contact contact) throws VivialConnectException {
        return contact.update();
    }

    @Override
    public boolean deleteContact(Contact contact) throws VivialConnectException {
        return contact.delete();
    }

    @Override
    public List<Contact> getContacts() throws VivialConnectException {
        return Contact.getContacts();
    }

    @Override
    public Contact getContactById(int contactId) throws VivialConnectException {
        return Contact.getContactById(contactId);
    }

    @Override
    public int contactCount() throws VivialConnectException {
        return Contact.count();
    }

    @Override
    public User createUser(Map<String, Object> attributes) throws VivialConnectException {
        return AdminUser.createUser(attributes);
    }

    @Override
    public boolean deleteUser(User user) throws VivialConnectException {
        return user.delete();
    }

    @Override
    public boolean updateUserPassword(User user, String oldPassword, String newPassword) throws VivialConnectException {
        return user.changePassword(oldPassword, newPassword);
    }

    @Override
    public List<User> getUsers() throws VivialConnectException {
        return User.getUsers();
    }

    @Override
    public User getUserById(int userId) throws VivialConnectException {
        return User.getUserById(userId);
    }

    @Override
    public int userCount() throws VivialConnectException {
        return User.count();
    }

    @Override
    public LogCollection getLogs(Date startTime, Date endTime) throws VivialConnectException {
        return Log.getLogs(startTime, endTime);
    }

    @Override
    public LogCollection getAggregate(Date startTime, Date endTime, String aggregatorType) throws VivialConnectException {
        return Log.getAggregate(startTime, endTime, aggregatorType);
    }

}