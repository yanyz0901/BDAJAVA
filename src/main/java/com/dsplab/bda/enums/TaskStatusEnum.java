package com.dsplab.bda.enums;

import org.springframework.util.StringUtils;

public enum TaskStatusEnum {

    UN_START("0", "未开始"),
    STARTING("1", "进行中"),
    COMPLETED("2", "已完成");

    String statusCode;
    String msg;

    TaskStatusEnum(String statusCode, String msg) {
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
        for (TaskStatusEnum value : values()) {
            if (value.getStatusCode().equals(code)) {
                return value.getMsg();
            }
        }
        return code;
    }
}
