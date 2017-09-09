package com.shijith.sms.bean;


import com.shijith.sms.bl.ValidationException;

public class SMSRequest {

    private static String FROM_MISSING = "from is missing";
    private static String FROM_INVALID = "from is invalid";

    private static String TO_MISSING = "to is missing";
    private static String TO_INVALID = "to is invalid";

    private static String TEXT_MISSING = "text is missing";
    private static String TEXT_INVALID = "text is invalid";


    private static Integer MIN_FROM_LENGTH = 6;
    private static Integer MAX_FROM_LENGTH = 16;

    private static Integer MIN_TO_LENGTH = 6;
    private static Integer MAX_TO_LENGTH = 16;

    private static Integer MIN_TEXT_LENGTH = 1;
    private static Integer MAX_TEXT_LENGTH = 120;


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

    public void validate() throws ValidationException {

        if(from == null || from.isEmpty()) new ValidationException(FROM_MISSING);
        if(to == null || to.isEmpty()) new ValidationException(TO_MISSING);
        if(text == null || text.isEmpty()) new ValidationException(TEXT_MISSING);

        if(from.length() < MIN_FROM_LENGTH || from.length() > MAX_FROM_LENGTH)
            throw new ValidationException(FROM_INVALID);

        if(to.length() < MIN_TO_LENGTH || to.length() > MAX_TO_LENGTH)
            throw new ValidationException(TO_INVALID);

        if(text.length() < MIN_TEXT_LENGTH || text.length() > MAX_TEXT_LENGTH)
            throw new ValidationException(TEXT_INVALID);

    }

}
