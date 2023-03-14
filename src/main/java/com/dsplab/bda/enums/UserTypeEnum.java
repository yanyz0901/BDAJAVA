package com.dsplab.bda.enums;

import org.springframework.util.StringUtils;

public enum UserTypeEnum {
    GENERAL_USER("0", "普通用户"),
    ADMIN("1", "管理员");

    String statusCode;
    String msg;

    UserTypeEnum(String statusCode, String msg) {
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
        for (UserTypeEnum value : values()) {
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
        for (UserTypeEnum value : values()) {
            if (value.getMsg().equals(msg)) {
                return value.getStatusCode();
            }
        }
        return msg;
    }
}
