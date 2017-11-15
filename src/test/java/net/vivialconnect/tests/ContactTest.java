package net.vivialconnect.tests;

import static org.junit.Assert.*;

import java.lang.Integer;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import net.vivialconnect.model.account.Contact;
import net.vivialconnect.model.error.VivialConnectException;

public class ContactTest extends BaseTestCase {

    @Test
    public void test_create_update_delete_contact() throws VivialConnectException {
        List<Contact> existingContacts = getDataSource().getContacts();
        int numContacts = existingContacts.size();

        for (Contact c: existingContacts) {
            // Delete existing contacts for this account to prevent a collision
            assertTrue(c.delete());
        }

        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Contact");
        contact.setEmail("john.contact.@unittest.com");
        contact.setContactType("main");

        Contact createdContact = getDataSource().createContact(contact);

        assertEquals(createdContact.getFirstName(), "John");
        assertEquals(createdContact.getLastName(), "Contact");
        assertEquals(createdContact.getEmail(), "john.contact.@unittest.com");
        assertEquals(createdContact.getContactType(), "main");

        createdContact.setCity("Anytown");
        Contact updatedContact = createdContact.update();
        assertEquals(updatedContact.getCity(), "Anytown");
    }

    @Test
    public void test_get_contacts() throws VivialConnectException {
        List<Contact> contacts = getDataSource().getContacts();
        assertTrue(contacts != null);
    }
}