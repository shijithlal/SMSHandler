package com.shijith.sms.controller;

import com.shijith.sms.bl.Processor;
import com.shijith.sms.bl.Validator;
import com.shijith.sms.bean.AuthenticatedUser;
import com.shijith.sms.bean.PhoneNumber;
import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bean.SMSHandlerResponse;
import com.shijith.sms.db.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inbound/sms/")
public class InboundSMSController {

    @Autowired
    Processor processor;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public SMSHandlerResponse post(@RequestBody SMSRequest request) {
        return processor.processInboundRequest(request);
    }
}
