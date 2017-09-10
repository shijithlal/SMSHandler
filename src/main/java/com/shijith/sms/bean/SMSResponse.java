package com.shijith.sms.bean;

public class SMSResponse {
    private String message;
    private String error;

    public SMSResponse() {

    }

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
    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(String error) {
        this.error = error;
    }


}
