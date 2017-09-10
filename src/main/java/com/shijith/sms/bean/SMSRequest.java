package com.shijith.sms.bean;


import com.shijith.sms.bl.ValidationException;

import static com.shijith.sms.Constants.*;

public class SMSRequest {


    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String from;
    private String to;
    private String text;

    public SMSRequest() {

    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    /**
     * Validate input request.
     * @throws ValidationException
     */
    public void validate() throws ValidationException {

        if(from == null || from.isEmpty()) throw new ValidationException(FROM_MISSING);
        if(to == null || to.isEmpty()) throw new ValidationException(TO_MISSING);
        if(text == null || text.isEmpty()) throw new ValidationException(TEXT_MISSING);

        if(from.length() < MIN_FROM_LENGTH || from.length() > MAX_FROM_LENGTH)
            throw new ValidationException(FROM_INVALID);

        if(to.length() < MIN_TO_LENGTH || to.length() > MAX_TO_LENGTH)
            throw new ValidationException(TO_INVALID);

        if(text.length() < MIN_TEXT_LENGTH || text.length() > MAX_TEXT_LENGTH)
            throw new ValidationException(TEXT_INVALID);

    }

}
