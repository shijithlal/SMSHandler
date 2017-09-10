package com.shijith.sms.controller;

import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bean.SMSResponse;
import com.shijith.sms.bl.IProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for /inbound/sms/
 */
@RestController
@RequestMapping("/inbound/sms/")
public class InboundSMSController {

    @Autowired
    private IProcessor processor;

    /**
     * Default constructor
     */
    public InboundSMSController() {

    }

    /**
     * Constructor for Unit test case.
     * @param processor
     */
    protected InboundSMSController(IProcessor processor) {
        this.processor = processor;
    }

    /**
     * /inbound/sms/ Post method
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public SMSResponse post(@RequestBody SMSRequest request) {
        return processor.processInboundRequest(request);
    }
}
