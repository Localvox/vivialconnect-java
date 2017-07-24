package net.vivialconnect.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.message.Message;
import net.vivialconnect.model.number.AssociatedNumber;

public class MessageTest extends BaseTestCase {

    private static String TO_NUMBER = "+18099667830";
    private static String MESSAGE_BODY = "Message from Vivial Connect Test Suite";

    @Test
    public void test_send_message() throws VivialConnectException {
        AssociatedNumber number = getDataSource().getAssociatedNumbers().get(0);
        String fromNumber = number.getPhoneNumber();

        Message message = new Message();
        message.setFromNumber(fromNumber);
        message.setToNumber(TO_NUMBER);
        message.setBody(MESSAGE_BODY);
        message.addMediaUrl("https://code.org/images/apple-touch-icon-precomposed.png");

        assertEquals(0, message.getId());

        getDataSource().sendMessage(message);

        assertTrue(message.getId() > 0);
        assertNotNull(message.getDateCreated());
        assertEquals(MESSAGE_BODY, message.getBody());
        assertEquals(fromNumber, message.getFromNumber());
        assertEquals(TO_NUMBER, message.getToNumber());
        assertEquals("local_mms", message.getMessageType());
        assertNotEquals("delivered", message.getStatus());
    }

    @Test
    public void test_message_count() throws VivialConnectException {
        assertTrue(messageCount() > 0);
    }

    @Test
    public void test_get_message() throws VivialConnectException {
        Message message = getMessageById();

        assertNotNull(message);
        assertTrue(message.getId() > 0);
        assertNotNull(message.getDateCreated());
        assertNotNull(message.getDateModified());
        assertNotNull(message.getStatus());
        assertNotNull(message.getBody());
    }

    @Test
    public void test_get_messages() throws VivialConnectException {
        assertEquals(getMessages().size(), messageCount());
    }

    @Test
    public void test_get_messages_with_limit() throws VivialConnectException {
        assertEquals(getMessages(withLimitOf(1)).size(), 1);
    }

    @Test(expected = VivialConnectException.class)
    public void test_get_message_with_invalid_id_throws_vivial_connect_exception() throws VivialConnectException {
        getMessageById(0);
    }

    @Test
    public void test_message_not_found() throws VivialConnectException {
        assertNull(getMessageById(1));
    }

    @Test
    public void test_redact_message() throws VivialConnectException {
        Message message = getMessageById();

        assertNotNull(message.getBody());

        redactMessage(message);

        assertEquals("", message.getBody());
        assertNotNull(message.getDateModified().getTime());
    }

    private List<Message> getMessages() throws VivialConnectException {
        return getMessages(null);
    }

    private List<Message> getMessages(Map<String, String> filters) throws VivialConnectException {
        return getDataSource().getMessages(filters);
    }

    private Message getMessageById(int messageId) throws VivialConnectException {
        return getDataSource().getMessageById(messageId);
    }

    private Message getMessageById() throws VivialConnectException {
        return getDataSource().getMessageById(getMessages().get(0).getId());
    }

    private int messageCount() throws VivialConnectException {
        return getDataSource().messageCount();
    }

    private void redactMessage(Message message) throws VivialConnectException {
        getDataSource().redactMessage(message);
    }
}