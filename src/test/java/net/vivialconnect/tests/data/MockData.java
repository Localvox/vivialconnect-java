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
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;
import net.vivialconnect.model.number.NumberCollection;
import net.vivialconnect.model.number.NumberInfo;
import net.vivialconnect.tests.BaseTestCase;

public class MockData implements DataSource {

    private List<AvailableNumber> availableNumbers;
    private List<AssociatedNumber> associatedNumbers;

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
            String errorMessage = String.format("Invalid id param %d", numberId);

            VivialConnectException vce = new VivialConnectException(errorMessage, new IOException());
            vce.setResponseCode(400);

            throw vce;
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
    public List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException {
        return applyFilters(loadAvailableNumbersFromFixture(), filters);
    }
    
    @Override
    public int numberCount() throws VivialConnectException {
    	return loadFixture("number-count", ResourceCount.class, false).getCount();
    }
    
    @Override
    public List<AssociatedNumber> getLocalAssociatedNumbers() throws VivialConnectException {
    	List<AssociatedNumber> localAssociatedNumbers = new ArrayList<AssociatedNumber>();
    	for (AssociatedNumber associatedNumber : loadAssociatedNumbersFromFixture())
		{
			if (associatedNumber.getPhoneNumberType().equals("local")) {
				localAssociatedNumbers.add(associatedNumber);
			}
		}
    	
    	return localAssociatedNumbers;
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
    public NumberInfo numberLookup(AssociatedNumber number) throws VivialConnectException {
    	NumberInfo info = loadFixture("number-info", NumberInfo.class);
    	if (info.getPhoneNumber().equals(number.getPhoneNumber().substring(1))) {
			return info;
		}
    	
    	return null;
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
}