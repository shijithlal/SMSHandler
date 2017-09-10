package com.shijith.sms.controller;

import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bl.IProcessor;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class InboundSMSControllerTest {
    @Test
    public void post() throws Exception {
        SMSRequest request = new SMSRequest();
        IProcessor processor = mock(IProcessor.class);
        InboundSMSController controller = new InboundSMSController(processor);
        controller.post(request);
        verify(processor,times(1)).processInboundRequest(any(SMSRequest.class));
    }
}