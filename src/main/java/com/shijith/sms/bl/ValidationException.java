package com.shijith.sms.bl;

public class ValidationException extends Exception {
    private ValidationException() {

    }
    public ValidationException(String error) {
        super(error);
    }
}
