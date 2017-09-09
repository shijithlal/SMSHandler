package com.shijith.sms;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String TO_NOTFOUND = "to parameter not found";
    public static final String FROM_NOTFOUND = "from parameter not found";
    public static final String UNKNOWN_ERROR = "unknown failure";
    public static final String INBOUND_END_POINT = "inbound";
    public static final String OUTBOUND_END_POINT = "outbound";
    public static final String SPACE = " ";
    public static final String OK_MESSAGE = "sms ok";

    public static final String AUTH_HEADER = "Authorization";

    public static final String SEPERATOR = ",";
    public static final String EQUALS = "=";
    public static final String USER_NAME = "UserName";
    public static final String PASSWORD = "Password";

    public static final String BLOCKED_MESSAGE = "sms from %s to %s blocked by STOP request";

    public static final String COUNTER_SUFFIX = "_COUNTER";

    public static final Integer COUNTER_EXPIRE_TIME = 24 * 60 * 60;

    public static final Integer KEY_EXPIRE_TIME =  4 * 60 * 60;

    public static final Integer API_CALL_LIMIT = 50;

    public static final String API_CALL_LIMIT_REACHED = "limit reached for from %s";

    public static final List<String> STOP_KEYWORD_LIST = new ArrayList<String>() {{
        add("STOP");
        add("STOP\r");
        add("STOP\r\n");
        add("STOP\n");
    }};

}
