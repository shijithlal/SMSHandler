package com.shijith.sms.auth;
import com.shijith.sms.bean.Account;
import org.springframework.stereotype.Service;


@Service
public interface IAuthValidator {
    Account validate(String authString) throws AuthException;
}
