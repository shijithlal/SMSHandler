package com.shijith.sms.bean;

import com.shijith.sms.Constants;
import com.shijith.sms.bl.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SMSRequestTest {
    @Test
    public void validate() {
        try {
            SMSRequest request = new SMSRequest();
            request.setFrom("12345687");
            request.setTo("12345678");
            request.setText("Hello");

            request.validate();
        }
        catch (ValidationException e) {
            Assert.fail();
        }
    }

    @Test
    public void validateNullFrom() {
        try {
            SMSRequest request = new SMSRequest();
            request.setFrom(null);
            request.setTo("12345678");
            request.setText("Hello");

            request.validate();
        }
        catch (ValidationException e) {
            Assert.assertEquals(Constants.FROM_MISSING, e.getMessage());
        }
    }

    @Test
    public void validateWrongFrom1() {
        try {
            SMSRequest request = new SMSRequest();
            request.setFrom("1234");
            request.setTo("12345678");
            request.setText("Hello");

            request.validate();
        }
        catch (ValidationException e) {
            Assert.assertEquals(Constants.FROM_INVALID, e.getMessage());
        }
    }

    @Test
    public void validateWrongFrom2() {
        try {
            SMSRequest request = new SMSRequest();
            request.setFrom("1234567890123456789");
            request.setTo("12345678");
            request.setText("Hello");

            request.validate();
        }
        catch (ValidationException e) {
            Assert.assertEquals(Constants.FROM_INVALID, e.getMessage());
        }
    }

    @Test
    public void validateNullTo() {
        try {
            SMSRequest request = new SMSRequest();
            request.setFrom("12345687");
            request.setTo(null);
            request.setText("Hello");

            request.validate();
        }
        catch (ValidationException e) {
            Assert.assertEquals(Constants.TO_MISSING, e.getMessage());
        }
    }

    @Test
    public void validateWrongTo1() {
        try {
            SMSRequest request = new SMSRequest();
            request.setFrom("12345687");
            request.setTo("1234");
            request.setText("Hello");

            request.validate();
        }
        catch (ValidationException e) {
            Assert.assertEquals(Constants.TO_INVALID, e.getMessage());
        }
    }

    @Test
    public void validateWrongTo2() {
        try {
            SMSRequest request = new SMSRequest();
            request.setFrom("12345687");
            request.setTo("1234567890123456789");
            request.setText("Hello");

            request.validate();
        }
        catch (ValidationException e) {
            Assert.assertEquals(Constants.TO_INVALID, e.getMessage());
        }
    }

    @Test
    public void validateNullText() {
        try {
            SMSRequest request = new SMSRequest();
            request.setFrom("12345687");
            request.setTo("12345678");
            request.setText(null);

            request.validate();
        }
        catch (ValidationException e) {
            Assert.assertEquals(Constants.TEXT_MISSING, e.getMessage());
        }
    }

    @Test
    public void validateInvalidText() {
        try {
            SMSRequest request = new SMSRequest();
            request.setFrom("12345687");
            request.setTo("12345678");
            request.setText("abcdefghij klmnopqrst uvwxyz abcdefghij klmnopqrst uvwxyz abcdefghij klmnopqrst uvwxyz abcdefghij klmnopqrst uvwxyz abcdefghij klmnopqrst uvwxyz abcdefghij klmnopqrst uvwxyz abcdefghij klmnopqrst uvwxyz abcdefghij klmnopqrst uvwxyz abcdefghij klmnopqrst uvwxyz abcdefghij klmnopqrst uvwxyz ");

            request.validate();
        }
        catch (ValidationException e) {
            Assert.assertEquals(Constants.TEXT_INVALID, e.getMessage());
        }
    }

    @Test
    public void validateWrongText() {
        try {
            SMSRequest request = new SMSRequest();
            request.setFrom("12345687");
            request.setTo("12345678");
            request.setText("");

            request.validate();
        }
        catch (ValidationException e) {
            Assert.assertEquals(Constants.TEXT_MISSING, e.getMessage());
        }
    }


}