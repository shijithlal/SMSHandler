package com.shijith.sms.bl;

import com.shijith.sms.bean.AuthenticatedUser;
import com.shijith.sms.bean.PhoneNumber;
import com.shijith.sms.bean.SMSHandlerResponse;
import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.db.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Processor {

    private static final String TO_NOTFOUND = "to parameter not found";
    private static final String FROM_NOTFOUND = "from parameter not found";
    private static final String UNKNOWN_ERROR = "unknown failure";

    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Autowired
    Validator validator;

    public SMSHandlerResponse processInboundRequest(SMSRequest request) {
        try {
            SMSHandlerResponse response = validator.validateInput(request);
            if (response != null) {
                return response;
            }
            AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();

            List<PhoneNumber> phoneNumbers = phoneNumberRepository.findPhoneNumberByAccount_id(authenticatedUser.getUserId());

            if (!phoneNumbers.contains(request.getTo())) {
                return new SMSHandlerResponse("", TO_NOTFOUND);
            }

            AuthenticatedUser.remove();
            return new SMSHandlerResponse("", "");
        }
        catch (Exception ex) {
            return new SMSHandlerResponse("",UNKNOWN_ERROR);
        }

    }
    public SMSHandlerResponse processOutboundRequest(SMSRequest request) {
        SMSHandlerResponse response = validator.validateInput(request);
        if(response != null) {
            return response;
        }

        AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();

        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findPhoneNumberByAccount_id(authenticatedUser.getUserId());

        if (!phoneNumbers.contains(request.getFrom())) {
            return new SMSHandlerResponse("", FROM_NOTFOUND);
        }
        
        AuthenticatedUser.remove();
        return new SMSHandlerResponse("","");
    }
}
