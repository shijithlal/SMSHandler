package com.shijith.sms.bl;

import org.junit.Assert;
import org.junit.Test;

public class ValidationExceptionTest {
    @Test
    public void testConstructor() {
        ValidationException validationException = new ValidationException("MyMessage");
        Assert.assertEquals("MyMessage", validationException.getMessage());
    }

}