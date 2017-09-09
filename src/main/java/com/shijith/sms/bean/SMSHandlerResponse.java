package com.shijith.sms.bean;

public class SMSHandlerResponse {
    private final String message;
    private final String error;

    public SMSHandlerResponse(String message, String error) {
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
