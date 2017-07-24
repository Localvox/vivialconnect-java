package net.vivialconnect.tests.data;

import java.util.List;
import java.util.Map;

import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;

public interface DataSource {

    Account getAccount() throws VivialConnectException;

    void updateAccount(Account account) throws VivialConnectException;

    AssociatedNumber getNumberById(int numberId) throws VivialConnectException;

    List<AssociatedNumber> getAssociatedNumbers() throws VivialConnectException;

    List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException;
}