package com.dsplab.bda.enums;

import org.springframework.util.StringUtils;

public enum UserStatusEnum {

    NORMAL("0", "正常"),
    DISABLED("1", "禁用");

    String statusCode;
    String msg;

    UserStatusEnum(String statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public static String getByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (UserStatusEnum value : values()) {
            if (value.getStatusCode().equals(code)) {
                return value.getMsg();
            }
        }
        return code;
    }

    public static String getByMsg(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return null;
        }
        for (UserStatusEnum value : values()) {
            if (value.getMsg().equals(msg)) {
                return value.getStatusCode();
            }
        }
        return msg;
    }
}
