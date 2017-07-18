package com.vivialconnect.tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vivialconnect.client.VivialConnectClient;
import com.vivialconnect.model.account.Account;
import com.vivialconnect.model.error.VivialConnectException;

public class AccountTest extends BaseTestCase {

    private Account testAccount;

    @BeforeClass
    public static void initializeClient() {
        VivialConnectClient.init(ACCOUNT_ID, API_KEY, API_SECRET);
    }
    
    
    @Before
    public void loadAccount() {
        if (testAccount == null) {
            testAccount = loadFixture("account", Account.class);
        }
    }
    
    
    @Test
    public void test_get_account() throws VivialConnectException {
        Account account = Account.getAccount();

        assertEquals(testAccount.getId(), account.getId());
    }
    
    
    @Test
    public void test_update_account() throws VivialConnectException {
        Account account = Account.getAccount();
        Date modifiedDateBeforeUpdate = account.getDateModified();

        String newCompanyName = getNewCompanyName(account);
        account.setCompanyName(newCompanyName);
        account.update();

        assertEquals(newCompanyName, account.getCompanyName());
        assertTrue(account.getDateModified().getTime() > modifiedDateBeforeUpdate.getTime());
    }
    

    private String getNewCompanyName(Account account) {
        String testAccountCompanyName = testAccount.getCompanyName();
        String newCompanyName = testAccountCompanyName;

        if (testAccountCompanyName.equals(account.getCompanyName())) {
            newCompanyName = "Vivial Connect";
        }

        return newCompanyName;
    }
}
