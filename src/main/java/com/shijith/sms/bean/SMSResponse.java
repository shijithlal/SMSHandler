package com.shijith.sms.bean;

public class SMSResponse {
    private final String message;
    private final String error;

    public SMSResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
