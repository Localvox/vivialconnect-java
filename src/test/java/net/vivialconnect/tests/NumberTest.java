package net.vivialconnect.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;

public class NumberTest extends BaseTestCase {
	
    @Test
    public void test_get_associated_numbers() throws VivialConnectException {
        assertTrue(getAssociatedNumbers().size() > 0);
    }
    
    @Test
    public void test_get_associated_number() throws VivialConnectException {
        AssociatedNumber associatedNumber = getNumberById();

        assertTrue(associatedNumber.getId() > 0);
    }

    @Test(expected = VivialConnectException.class)
    public void test_get_associated_number_with_invalid_id_throws_vivial_connect_exception() throws VivialConnectException {
        getNumberById(0);
    }

    /* @Test
    public void test_number_not_found() throws VivialConnectException {
        assertNull(getNumberById(1));
    } */

    @Test
    public void test_find_available_numbers_by_area_code_with_limit() throws VivialConnectException {
        List<AvailableNumber> availableNumbers = findAvailableNumbersByAreaCode("302", withLimitOf(2));
		
        if (availableNumbers.size() > 0) {
            assertEquals(2, availableNumbers.size());
        }
    }

    @Test
    public void test_get_local_associated_numbers() throws VivialConnectException {
        assertEquals("local", getLocalAssociatedNumbers().get(0).getPhoneNumberType());
    }

    @Test
    public void test_number_count() throws VivialConnectException {
        assertEquals(getAssociatedNumbers().size(), getDataSource().numberCount());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_delete_local_number_with_non_local_type_throws_unsupported_operation_exception() throws VivialConnectException {
        AssociatedNumber localNumber = getLocalAssociatedNumbers().get(0);
        localNumber.setPhoneNumberType("tollfree");
        
        getDataSource().deleteLocalNumber(localNumber);
    }

    @Test
    public void test_update_number() throws VivialConnectException {
        AssociatedNumber associatedNumber = getNumberById();
        Date dateModifiedBeforeUpdate = associatedNumber.getDateModified();

        String newIncomingTextUrl = getNewIncomingTextUrl(associatedNumber);
        associatedNumber.setIncomingTextUrl(newIncomingTextUrl);
        
        updateNumber(associatedNumber);

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
    	AssociatedNumber number = getNumberById();
        assertEquals(number.getPhoneNumber().substring(1), getDataSource().numberLookup(number).getPhoneNumber());
    }
    
    private List<AssociatedNumber> getAssociatedNumbers() throws VivialConnectException {
    	return getDataSource().getAssociatedNumbers();
    }
    
    private List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException {
    	return getDataSource().findAvailableNumbersByAreaCode(areaCode, filters);
    }
    
    private AssociatedNumber getNumberById() throws VivialConnectException {
    	return getNumberById(getAssociatedNumbers().get(0).getId());
    }
    
    private AssociatedNumber getNumberById(int numberId) throws VivialConnectException {
    	return getDataSource().getNumberById(numberId);
    }
    
    private List<AssociatedNumber> getLocalAssociatedNumbers() throws VivialConnectException {
    	return getDataSource().getLocalAssociatedNumbers();
    }
    
    private void updateNumber(AssociatedNumber number) throws VivialConnectException {
    	getDataSource().updateNumber(number);
    }
}