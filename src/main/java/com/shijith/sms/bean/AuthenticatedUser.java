package com.shijith.sms.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

public class AuthenticatedUser{

    private Integer userId;

    private static ThreadLocal<AuthenticatedUser> threadLocal = new ThreadLocal<>();

    public static AuthenticatedUser getAuthenticatedUser() {
        AuthenticatedUser authenticatedUser = threadLocal.get();
        if(authenticatedUser == null) {
            authenticatedUser = new AuthenticatedUser();
            threadLocal.set(authenticatedUser);
        }
        return authenticatedUser;
    }

    public static void remove() {
        threadLocal.remove();
    }

    private AuthenticatedUser() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
