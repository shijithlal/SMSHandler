package com.shijith.sms.auth;
import com.shijith.sms.bean.Account;
import org.springframework.stereotype.Service;


@Service
public interface IAuthValidator {
    /**
     * Validate authentication string in the request and return a Account object if authentication is success.
     * Throw AuthException if the credential does not match.
     * @param authString
     * @return
     * @throws AuthException
     */
    Account validate(String authString) throws AuthException;
}
