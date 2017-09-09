package com.shijith.sms.bl;

import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bean.SMSResponse;

public interface IProcessor {
    SMSResponse processInboundRequest(SMSRequest request);
    SMSResponse processOutboundRequest(SMSRequest request);
}
