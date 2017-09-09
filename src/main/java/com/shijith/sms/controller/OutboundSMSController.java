package com.shijith.sms.controller;

import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bean.SMSResponse;
import com.shijith.sms.bl.IProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OutboundSMSController {

    @Autowired
    IProcessor processor;

    @RequestMapping(value = "/outbound/sms/", method = RequestMethod.POST)
    public SMSResponse post(@RequestBody SMSRequest request) {
        return processor.processOutboundRequest(request);
    }
}
