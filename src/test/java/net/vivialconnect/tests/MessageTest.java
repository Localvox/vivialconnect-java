package net.vivialconnect.tests;

import net.vivialconnect.client.VivialConnectClient;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.message.Message;
import java.util.HashMap;

import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest extends BaseTestCase {

    /* private static String FROM_NUMBER = "+13022136859";
    private static String TO_NUMBER = "+18099667830";
    private static String MESSAGE_BODY = "Message from Vivial Connect Test Suite";

    private static int messageId = 0;

    @BeforeClass
    public static void initializeClient() {
        VivialConnectClient.init(ACCOUNT_ID, API_KEY, API_SECRET);
    }

    @Test
    public void test_send_message() throws VivialConnectException {
        Message message = new Message();
        message.setFromNumber(FROM_NUMBER);
        message.setToNumber(TO_NUMBER);
        message.setBody(MESSAGE_BODY);
        message.addMediaUrl("https://code.org/images/apple-touch-icon-precomposed.png");

        assertEquals(0, message.getId());

        message.send();
        this.messageId = message.getId();

        assertTrue(this.messageId > 0);
        assertEquals(MESSAGE_BODY, message.getBody());
        assertEquals(FROM_NUMBER, message.getFromNumber());
        assertEquals(TO_NUMBER, message.getToNumber());
        assertEquals("local_mms", message.getMessageType());
        assertNotEquals("delivered", message.getStatus());
    }

    @Test
    public void test_message_count() throws VivialConnectException {
        assertTrue(Message.count() > 0);
    }

    @Test
    public void test_get_message() throws VivialConnectException {
        assertTrue(this.messageId > 0);
        Message message = Message.getMessageById(messageId);

        assertNotNull(message);
        assertEquals(this.messageId, message.getId());
        assertEquals(1, message.getNumMedia());
    }

    @Test
    public void test_get_messages() throws VivialConnectException {
        assertEquals(Message.getMessages().size(), Message.count());
    }

    @Test
    public void test_get_messages_with_limit() throws VivialConnectException {
        Map<String, String> filters = new HashMap<String, String>();
        filters.put("limit", "1");

        assertEquals(Message.getMessages(filters).size(), 1);
    }

    @Test(expected = VivialConnectException.class)
    public void test_get_message_with_invalid_id_throws_vivial_connect_exception() throws VivialConnectException {
        Message.getMessageById(0);
    }

    @Test
    public void test_message_not_found() throws VivialConnectException {
        assertNull(Message.getMessageById(1));
    }

    @Test
    public void test_redact_message() throws VivialConnectException {
        Message message = Message.getMessageById(messageId);

        assertEquals(MESSAGE_BODY, message.getBody());
        assertEquals("", message.redact().getBody());
    } */
}
