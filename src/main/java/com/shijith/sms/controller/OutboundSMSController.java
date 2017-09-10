package com.shijith.sms.controller;

import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bean.SMSResponse;
import com.shijith.sms.bl.IProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for /outbound/sms/
 */
@RestController
@RequestMapping("/outbound/sms/")
public class OutboundSMSController {

    @Autowired
    private IProcessor processor;

    /**
     * Default constructor
     */
    public OutboundSMSController() {

    }

    /**
     * Constructor for unit test case.
     * @param processor
     */
    protected OutboundSMSController(IProcessor processor) {
        this.processor = processor;
    }

    /**
     * /outbound/sms/ Post Method
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public SMSResponse post(@RequestBody SMSRequest request) {
        return processor.processOutboundRequest(request);
    }
}
