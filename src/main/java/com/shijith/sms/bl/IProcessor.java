package com.shijith.sms.bl;

import com.shijith.sms.bean.SMSRequest;
import com.shijith.sms.bean.SMSResponse;

public interface IProcessor {
    /**
     * Process the inbound end point request
     * @param request
     * @return
     */
    SMSResponse processInboundRequest(SMSRequest request);

    /**
     * Process the outbound end point request
     * @param request
     * @return
     */

    SMSResponse processOutboundRequest(SMSRequest request);
}
