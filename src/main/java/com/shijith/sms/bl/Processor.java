package com.shijith.sms.bl;

import com.shijith.sms.bean.AuthenticatedUser;
import com.shijith.sms.bean.PhoneNumber;
import com.shijith.sms.bean.SMSResponse;
import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.cache.ICacheService;
import com.shijith.sms.db.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shijith.sms.Constants.*;

@Service
public class Processor implements IProcessor{


    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Autowired
    ICacheService cacheService;

    public SMSResponse processInboundRequest(SMSRequest request) {
        SMSResponse response;
        try {
            request.validate();
            AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
            List<PhoneNumber> phoneNumbers = phoneNumberRepository.findPhoneNumberByAccount_id(authenticatedUser.getUserId());
            if (!isListContainsPhoneNumber(phoneNumbers,request.getTo())) {
                response = new SMSResponse("", TO_NOTFOUND);
            }
            else {
                if(STOP_KEYWORD_LIST.contains(request.getText())) {
                    cacheService.add(request.getFrom(),request.getTo());
                }
                response = new SMSResponse(INBOUND_END_POINT + SPACE + OK_MESSAGE, "");
            }

        }
        catch (ValidationException e) {
            response = new SMSResponse("",e.getMessage());
        }
        catch (Exception ex) {
            response = new SMSResponse("",UNKNOWN_ERROR);
        }
        AuthenticatedUser.remove();
        return response;

    }
    public SMSResponse processOutboundRequest(SMSRequest request) {

        SMSResponse response;
        try {
            request.validate();
            AuthenticatedUser authenticatedUser = AuthenticatedUser.getAuthenticatedUser();
            List<PhoneNumber> phoneNumbers = phoneNumberRepository.findPhoneNumberByAccount_id(authenticatedUser.getUserId());
            String cacheValue = cacheService.get(request.getFrom());
            Integer counterValue = cacheService.increment(request.getFrom());
            if (!isListContainsPhoneNumber(phoneNumbers,request.getFrom())) {
                response = new SMSResponse("", FROM_NOTFOUND);
            }
            else if(request.getTo().equals(cacheValue)) {
                response = new SMSResponse("", String.format(BLOCKED_MESSAGE,request.getFrom(),request.getTo()));
            }
            else if(counterValue > API_CALL_LIMIT) {
                response = new SMSResponse("",String.format(API_CALL_LIMIT_REACHED,request.getFrom()));
            }
            else {

                response = new SMSResponse(OUTBOUND_END_POINT + SPACE + OK_MESSAGE, "");
            }

        }
        catch (ValidationException e) {
            response = new SMSResponse("",e.getMessage());
        }
        catch (Exception ex) {
            response = new SMSResponse("",UNKNOWN_ERROR);
        }
        AuthenticatedUser.remove();
        return response;
    }

    private boolean isListContainsPhoneNumber(List<PhoneNumber> phoneNumbers, String strPhoneNumber) {
        for(PhoneNumber phoneNumber : phoneNumbers) {
            if(phoneNumber.getNumber().equals(strPhoneNumber)) {
                return true;
            }
        }
        return false;
    }
}
