package net.vivialconnect.tests.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.account.Contact;
import net.vivialconnect.model.account.ContactCollection;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.message.Message;
import net.vivialconnect.model.message.MessageCollection;
import net.vivialconnect.model.message.Attachment;
import net.vivialconnect.model.message.AttachmentCollection;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;
import net.vivialconnect.model.number.NumberCollection;
import net.vivialconnect.model.number.NumberInfo;
import net.vivialconnect.model.user.User;
import net.vivialconnect.model.user.UserCollection;
import net.vivialconnect.tests.BaseTestCase;
import net.vivialconnect.model.log.Log;
import net.vivialconnect.model.log.LogCollection;

public class MockData implements DataSource {

    private List<AvailableNumber> availableNumbers;
    private List<AssociatedNumber> associatedNumbers;
    private List<Contact> contacts;
    private List<User> users;
    private List<Attachment> attachments;
    private List<Message> messages;
    private LogCollection logs;
    private int pendingCount = 0;

    @Override
    public Account getAccount() throws VivialConnectException {
        return loadFixture("account", Account.class);
    }

    @Override
    public void updateAccount(Account account) throws VivialConnectException {
        account.setDateModified(new Date());
    }

    @Override
    public AssociatedNumber getNumberById(int numberId) throws VivialConnectException {
        if (numberId < 1) {
            handleInvalidId(numberId);
        }

        return findAssociatedNumber(numberId);
    }

    @Override
    public AssociatedNumber getLocalNumberById(int numberId) throws VivialConnectException {
        if (numberId < 1) {
            handleInvalidId(numberId);
        }

        return findAssociatedNumber(numberId);
    }

    private AssociatedNumber findAssociatedNumber(int numberId) {
        for (AssociatedNumber associatedNumber : loadAssociatedNumbersFromFixture()) {
            if (associatedNumber.getId() == numberId) {
                return associatedNumber;
            }
        }

        return null;
    }

    @Override
    public List<AssociatedNumber> getAssociatedNumbers() throws VivialConnectException {
        return loadAssociatedNumbersFromFixture();
    }

    @Override
    public List<AssociatedNumber> getLocalAssociatedNumbers() throws VivialConnectException {
        List<AssociatedNumber> localAssociatedNumbers = new ArrayList<AssociatedNumber>();
        for (AssociatedNumber associatedNumber : loadAssociatedNumbersFromFixture()) {
            if (associatedNumber.getPhoneNumberType().equals("local")) {
                localAssociatedNumbers.add(associatedNumber);
            }
        }

        return localAssociatedNumbers;
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersInRegion(String region, Map<String, String> filters) throws VivialConnectException {
        return applyFilters(loadAvailableNumbersFromFixture(), filters);
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException {
        return applyFilters(loadAvailableNumbersFromFixture(), filters);
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersByPostalCode(String postalCode, Map<String, String> filters) throws VivialConnectException {
        return applyFilters(loadAvailableNumbersFromFixture(), filters);
    }

    @Override
    public int numberCount() throws VivialConnectException {
        return loadFixture("number-count", ResourceCount.class, false).getCount();
    }

    @Override
    public int numberCountLocal() throws VivialConnectException {
        return loadFixture("number-count-local", ResourceCount.class, false).getCount();
    }

    @Override
    public AssociatedNumber buy(String phoneNumber, String areaCode, String phoneNumberType, Map<String, Object> optionalParams) throws VivialConnectException {
        return loadAssociatedNumbersFromFixture().get(0);
    }

    @Override
    public AssociatedNumber buyLocalNumber(String phoneNumber, String areaCode, Map<String, Object> optionalParams) throws VivialConnectException {
        return loadAssociatedNumbersFromFixture().get(0);
    }

    @Override
    public void deleteLocalNumber(AssociatedNumber localNumber) throws VivialConnectException {
        if (!localNumber.getPhoneNumberType().equals("local")) {
            localNumber.setPhoneNumberType("local");
            throw new UnsupportedOperationException("Number must be local");
        }

        loadAssociatedNumbersFromFixture().remove(localNumber);
    }

    @Override
    public void updateNumber(AssociatedNumber number) throws VivialConnectException {
        number.setDateModified(new Date());
    }

    @Override
    public void updateLocalNumber(AssociatedNumber number) throws VivialConnectException {
        number.setDateModified(new Date());
    }

    @Override
    public NumberInfo numberLookup(AssociatedNumber number) throws VivialConnectException {
        NumberInfo info = loadFixture("number-info", NumberInfo.class);
        if (info.getPhoneNumber().equals(number.getPhoneNumber().substring(1))) {
            return info;
        }

        return null;
    }

    @Override
    public List<Message> getMessages(Map<String, String> filters) throws VivialConnectException {
        if (filters == null) {
            return loadMessagesFromFixture();
        }

        return applyFilters(loadMessagesFromFixture(), filters);
    }

    @Override
    public Message getMessageById(int messageId) throws VivialConnectException {
        if (messageId < 1) {
            handleInvalidId(messageId);
        }

        for (Message message : loadMessagesFromFixture()) {
            if (message.getId() == messageId) {
                return message;
            }
        }

        return null;
    }

    @Override
    public void sendMessage(Message message) throws VivialConnectException {
        message.setId(getMessages(null).get(0).getId() + 1);
        message.setDirection("outbound-api");
        message.setStatus("accepted");

        Date dateCreated = new Date();
        message.setDateCreated(dateCreated);
        message.setDateModified(dateCreated);

        int numMedia = message.getMediaUrls().size();
        if (numMedia > 0) {
            message.setNumMedia(numMedia);
            message.setMessageType("local_mms");
        }

        messages.add(0, message);
        pendingCount++;
    }

    @Override
    public int messageCount() throws VivialConnectException {
        return loadFixture("message-count", ResourceCount.class, false).getCount() + pendingCount;
    }

    @Override
    public void redactMessage(Message message) throws VivialConnectException {
        message.setBody("");
        message.setDateModified(new Date());
    }

    private List<Attachment> loadAttachmentsFromFixture() {
        if (attachments == null) {
            attachments = loadFixture("attachments", AttachmentCollection.class, false).getAttachments();
        }

        return attachments;
    }

    @Override
    public List<Attachment> getAttachments(Message message) throws VivialConnectException {
        return loadAttachmentsFromFixture();
    }

    @Override
    public Attachment getAttachmentById(int messageId, int attachmentId) throws VivialConnectException {
        Message message = getMessageById(messageId);
        List<Attachment> attachments = getAttachments(message);

        if (attachmentId < 1) {
            handleInvalidId(attachmentId);
        }

        for (Attachment attachment : attachments) {
            if (attachment.getId() == attachmentId) {
                return attachment;
            }
        }

        return null;
    }

    @Override
    public int attachmentCount(int messageId) throws VivialConnectException {
        return loadFixture("attachment-count", ResourceCount.class, false).getCount();
    }

    @Override
    public boolean deleteAttachment(Attachment attachment) throws VivialConnectException {
        return true;
    }

    private List<AssociatedNumber> loadAssociatedNumbersFromFixture() {
        if (associatedNumbers == null) {
            associatedNumbers = loadFixture("associated-numbers", NumberCollection.class, false).getAssociatedNumbers();
        }

        return associatedNumbers;
    }

    private List<AvailableNumber> loadAvailableNumbersFromFixture() {
        if (availableNumbers == null) {
            availableNumbers = loadFixture("available-numbers", NumberCollection.class, false).getAvailableNumbers();
        }

        return availableNumbers;
    }

    private List<Message> loadMessagesFromFixture() {
        if (messages == null) {
            messages = loadFixture("messages", MessageCollection.class, false).getMessages();
        }

        return messages;
    }

    private <T> List<T> applyFilters(List<T> elements, Map<String, String> filters) {
        /* TODO: int page = getPage(filters); */
        int limit = getLimit(filters);
        if (limit > 0) {
            elements = elements.subList(0, limit);
        }

        return elements;
    }

    private int getLimit(Map<String, String> filters) {
        String limit = filters.get("limit");
        if (limit == null) {
            limit = "0";
        }

        return Integer.parseInt(limit);
    }

    protected <T> T loadFixture(String filename, Class<T> type) {
        return loadFixture(filename, type, true);
    }

    protected <T> T loadFixture(String filename, Class<T> type, boolean unwrapRoot) {
        ClassLoader classLoader = BaseTestCase.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(String.format("%s.json", filename));

        try {
            String content = IOUtils.toString(stream);

            ObjectMapper mapper = getObjectMapper();
            if (unwrapRoot) {
                mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
            }

            return mapper.reader().forType(type).readValue(content);
        } catch (IOException ioe) {
            System.err.println(String.format("Failed to load fixture ('%s'): %s", filename, ioe));
        }

        return null;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    private void handleInvalidId(int id) throws VivialConnectException {
        String errorMessage = String.format("Invalid id param %d", id);

        VivialConnectException vce = new VivialConnectException(errorMessage, new IOException());
        vce.setResponseCode(400);

        throw vce;
    }

    @Override
    public Contact createContact(Contact contact) throws VivialConnectException {
        return contact;
    }

    @Override
    public Contact updateContact(Contact contact) throws VivialConnectException {
        return contact;
    }

    @Override
    public boolean deleteContact(Contact contact) throws VivialConnectException {
        return true;
    }

    @Override
    public Contact getContactById(int contactId) throws VivialConnectException {
        if (contactId < 1) {
            handleInvalidId(contactId);
        }

        for (Contact contact : loadContactsFromFixture()) {
            if (contact.getId() == contactId) {
                return contact;
            }
        }

        return null;
    }

    @Override
    public List<Contact> getContacts() throws VivialConnectException {
        return loadContactsFromFixture();
    }

    @Override
    public int contactCount() throws VivialConnectException {
        return loadFixture("contact-count", ResourceCount.class, false).getCount();
    }

    private List<Contact> loadContactsFromFixture() {
        if (contacts == null) {
            contacts = loadFixture("contacts", ContactCollection.class, false).getContacts();
        }

        return contacts;
    }

    @Override
    public User createUser(Map<String, Object> attributes) throws VivialConnectException {
        return getUsers().get(0);
    }

    @Override
    public boolean deleteUser(User user) throws VivialConnectException {
        return true;
    }

    @Override
    public boolean updateUserPassword(User user, String oldPassword, String newPassword) throws VivialConnectException {
        return true;
    }

    @Override
    public List<User> getUsers() throws VivialConnectException {
        return loadUsersFromFixture();
    }

    @Override
    public User getUserById(int userId) throws VivialConnectException {
        if (userId < 1) {
            handleInvalidId(userId);
        }

        for (User user : loadUsersFromFixture()) {
            if (user.getId() == userId) {
                return user;
            }
        }

        return null;
    }

    @Override
    public int userCount() throws VivialConnectException {
        return loadFixture("user-count", ResourceCount.class, false).getCount();
    }

    private List<User> loadUsersFromFixture() {
        if (users == null) {
            users = loadFixture("users", UserCollection.class, false).getUsers();
        }

        return users;
    }

    @Override
    public LogCollection getLogs(Date startTime, Date endTime) throws VivialConnectException {
        LogCollection logs = loadLogsFromFixture();
        return logs;
    }

    @Override
    public LogCollection getAggregate(Date startTime, Date endTime, String aggregatorType) throws VivialConnectException {
        return loadFixture("logs-aggregate-hours", LogCollection.class, false);
    }

    private LogCollection loadLogsFromFixture() {
        if (logs == null) {
            logs = loadFixture("logs", LogCollection.class, false);
        }

        return logs;
    }
}