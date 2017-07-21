package net.vivialconnect.tests;

import static org.junit.Assert.*;

import net.vivialconnect.client.VivialConnectClient;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;
import net.vivialconnect.model.number.Number;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NumberTest extends BaseTestCase {

    private AssociatedNumber number;

    @BeforeClass
    public static void initializeClient() {
        VivialConnectClient.init(ACCOUNT_ID, API_KEY, API_SECRET);
    }

    @Before
    public void loadNumber() {
        if (number == null) {
            number = (AssociatedNumber) loadFixture("associated-number", Number.class);
        }
    }

    @Test
    public void test_get_associated_numbers() throws VivialConnectException {
        assertTrue(Number.getAssociatedNumbers().size() > 0);
    }

    @Test
    public void test_get_associated_number() throws VivialConnectException {
        AssociatedNumber associatedNumber = Number.getNumberById(number.getId());

        assertEquals(number.getId(), associatedNumber.getId());
    }

    @Test(expected = VivialConnectException.class)
    public void test_get_associated_number_with_invalid_id_throws_vivial_connect_exception() throws VivialConnectException {
        Number.getNumberById(0);
    }

    @Test
    public void test_number_not_found() throws VivialConnectException {
        assertNull(Number.getNumberById(1));
    }

    @Test
    public void test_find_available_numbers_by_area_code_with_limit() throws VivialConnectException {
        String areaCode = "302";

        Map<String, String> filters = new HashMap<String, String>();
        filters.put("limit", "1");

        List<AvailableNumber> availableNumbers = Number.findAvailableNumbersByAreaCode(areaCode, filters);

        if (availableNumbers.size() > 0) {
            assertEquals(1, availableNumbers.size());
        }
    }

    @Test
    public void test_get_local_associated_numbers() throws VivialConnectException {
        assertEquals("local", Number.getLocalAssociatedNumbers().get(0).getPhoneNumberType());
    }

    @Test
    public void test_number_count() throws VivialConnectException {
        assertEquals(Number.getAssociatedNumbers().size(), Number.count());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_delete_local_number_with_non_local_type_throws_unsupported_operation_exception() throws VivialConnectException {
        AssociatedNumber localNumber = Number.getLocalAssociatedNumbers().get(0);
        localNumber.setPhoneNumberType("tollfree");
        localNumber.deleteLocalNumber();
    }

    @Test
    public void test_update_number() throws VivialConnectException {
        AssociatedNumber associatedNumber = Number.getNumberById(number.getId());
        Date dateModifiedBeforeUpdate = associatedNumber.getDateModified();

        String newIncomingTextUrl = getNewIncomingTextUrl(associatedNumber);
        associatedNumber.setIncomingTextUrl(newIncomingTextUrl);
        associatedNumber.update();

        assertTrue(associatedNumber.getDateModified().getTime() > dateModifiedBeforeUpdate.getTime());
        assertEquals(newIncomingTextUrl, associatedNumber.getIncomingTextUrl());
    }

    private String getNewIncomingTextUrl(AssociatedNumber associatedNumber) {
        if ("https://foo.bar/callback".equals(associatedNumber.getIncomingTextUrl())) {
            return "https://bar.foo/callback";
        } else {
            return "https://foo.bar/callback";
        }
    }

    @Test
    public void test_number_lookup() throws VivialConnectException {
        assertEquals(number.getPhoneNumber().substring(1), Number.getNumberById(number.getId()).lookup().getPhoneNumber());
    }
}
