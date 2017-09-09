package com.shijith.sms.auth;

import com.shijith.sms.bean.Account;
import com.shijith.sms.bean.AuthenticatedUser;
import com.shijith.sms.db.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthValidator implements IAuthValidator {
    private static final String SEPERATOR = ",";
    private static final String EQUALS = "=";
    private static final String USER_NAME = "UserName";
    private static final String PASSWORD = "Password";


    @Autowired
    private AccountRepository accountRepository;

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

