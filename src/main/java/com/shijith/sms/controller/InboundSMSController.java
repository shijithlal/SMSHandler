package com.shijith.sms.controller;

import com.shijith.sms.bl.IProcessor;
import com.shijith.sms.bl.Processor;
import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bean.SMSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inbound/sms/")
public class InboundSMSController {

    @Autowired
    IProcessor processor;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public SMSResponse post(@RequestBody SMSRequest request) {
        return processor.processInboundRequest(request);
    }
}
