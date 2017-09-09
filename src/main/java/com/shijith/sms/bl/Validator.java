package com.shijith.sms.bl;

import com.shijith.sms.bean.SMSHandlerResponse;
import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bl.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class Validator {

    public SMSHandlerResponse validateInput(SMSRequest request) {
        try {
            request.validate();
        } catch (ValidationException e) {
            SMSHandlerResponse response = new SMSHandlerResponse("",e.getMessage());
        }
        return null;
    }
}
