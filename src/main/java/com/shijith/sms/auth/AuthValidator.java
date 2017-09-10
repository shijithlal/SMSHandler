package com.shijith.sms.auth;

import com.shijith.sms.bean.Account;
import com.shijith.sms.db.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shijith.sms.Constants.*;

@Service
public class AuthValidator implements IAuthValidator {


    @Autowired
    private AccountRepository accountRepository;

    /**
     * Deffaut constructor
     */
    public AuthValidator() {

    }

    /**
     * Constructor for Unit test cases
     * @param accountRepository
     */
    protected AuthValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;

    }


    /**
     * Validate authentication string in the request and return a Account object if authentication is success.
     * Throw AuthException if the credential does not match.
     * @param authString
     * @return
     * @throws AuthException
     */
    @Override
    public Account validate(String authString) throws AuthException {
        Map<String,String> authCredentials = getCredetials(authString);

        String userName = authCredentials.get(USER_NAME);
        String password = authCredentials.get(PASSWORD);

        if(userName != null) {
            List<Account> accounts = accountRepository.findAccountByUsername(userName);
            for(Account account:accounts) {
                if (account != null) {
                    if (password != null && password.equals(account.getAuth_id())) {
                        return account;
                    }
                }
            }
        }

        throw new AuthException();

    }

    /**
     * Create a map from auth string.
     * @param authString
     * @return
     */
    private Map<String,String> getCredetials(String authString) {
        Map<String, String> retMap = new HashMap<>();
        String [] credentials = authString.split(SEPERATOR);
        for(String credential : credentials) {
            String [] keyValue = credential.split(EQUALS);
            if(keyValue.length == 2) {
                retMap.put(keyValue[0],keyValue[1]);
            }
        }
        return retMap;
    }

}

