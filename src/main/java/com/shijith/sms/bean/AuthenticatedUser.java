package com.shijith.sms.bean;

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
