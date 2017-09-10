package com.shijith.sms.auth;

import com.shijith.sms.bean.Account;
import com.shijith.sms.db.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthValidatorTest {

    @Before
    public void beforeMethod() {

    }

    @Test
    public void validate() {
        try {
            Account account = new Account();
            account.setUsername("correctUser");
            account.setAuth_id("correctAuthId");
            account.setId(1);

            List<Account> accounts = new ArrayList<Account>() {{
                add(account);
            }};

            AccountRepository accountRepository = mock(AccountRepository.class);
            when(accountRepository.findAccountByUsername("correctUser")).thenReturn(accounts);

            AuthValidator authValidator = new AuthValidator(accountRepository);
            authValidator.validate("UserName=correctUser,Password=correctAuthId");
        }
        catch (AuthException ex) {
            Assert.fail();
        }
    }

    @Test
    public void validateInvalidCredentials() {
        try {

            Account account = new Account();
            account.setUsername("correctUser");
            account.setAuth_id("correctAuthId");
            account.setId(1);

            List<Account> accounts = new ArrayList<Account>() {{
                add(account);
            }};

            AccountRepository accountRepository = mock(AccountRepository.class);
            when(accountRepository.findAccountByUsername("correctUser")).thenReturn(accounts);

            AuthValidator authValidator = new AuthValidator(accountRepository);
            authValidator.validate("UserName=correctUser,Password=wrongAuthId");

            Assert.fail();
        }
        catch (AuthException ex) {
        }
    }


    @Test
    public void validateMoreDataInAuthString() {
        try {
            Account account = new Account();
            account.setUsername("correctUser");
            account.setAuth_id("correctAuthId");
            account.setId(1);

            List<Account> accounts = new ArrayList<Account>() {{
                add(account);
            }};

            AccountRepository accountRepository = mock(AccountRepository.class);
            when(accountRepository.findAccountByUsername("correctUser")).thenReturn(accounts);

            AuthValidator authValidator = new AuthValidator(accountRepository);
            authValidator.validate("UserName=correctUser,Password=correctAuthId,abc=pqr");
        }
        catch (AuthException ex) {
            Assert.fail();
        }
    }

    @Test
    public void validateWithoutPassword() {
        try {
            Account account = new Account();
            account.setUsername("correctUser");
            account.setAuth_id("correctAuthId");
            account.setId(1);

            List<Account> accounts = new ArrayList<Account>() {{
                add(account);
            }};

            AccountRepository accountRepository = mock(AccountRepository.class);
            when(accountRepository.findAccountByUsername("correctUser")).thenReturn(accounts);

            AuthValidator authValidator = new AuthValidator(accountRepository);
            authValidator.validate("UserName=correctUser");
            Assert.fail();
        }
        catch (AuthException ex) {

        }
    }

    @Test
    public void validateWrongAuthString1() {
        try {
            Account account = new Account();
            account.setUsername("correctUser");
            account.setAuth_id("correctAuthId");
            account.setId(1);

            List<Account> accounts = new ArrayList<Account>() {{
                add(account);
            }};

            AccountRepository accountRepository = mock(AccountRepository.class);
            when(accountRepository.findAccountByUsername("correctUser")).thenReturn(accounts);

            AuthValidator authValidator = new AuthValidator(accountRepository);
            authValidator.validate("UserName=correctUser,Password=");
            Assert.fail();
        }
        catch (AuthException ex) {

        }
    }

    @Test
    public void validateWrongAuthString2() {
        try {
            Account account = new Account();
            account.setUsername("correctUser");
            account.setAuth_id("correctAuthId");
            account.setId(1);

            List<Account> accounts = new ArrayList<Account>() {{
                add(account);
            }};

            AccountRepository accountRepository = mock(AccountRepository.class);
            when(accountRepository.findAccountByUsername("correctUser")).thenReturn(accounts);

            AuthValidator authValidator = new AuthValidator(accountRepository);
            authValidator.validate("UserName=correctUser,Password=correctAuthId=pdq");
            Assert.fail();
        }
        catch (AuthException ex) {

        }
    }

}