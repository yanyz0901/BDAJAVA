package com.dsplab.bda.utils;

import java.util.regex.Pattern;

public class RegularCheckUtils {
    /**
     * 校验手机号
     * @param phone
     * @return boolean
     */
    public static boolean checkPhone(String phone) {
        if (phone != null && (!phone.isEmpty())) {
            return Pattern
                    .matches("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$", phone);
        }
        return false;
    }

    /**
     * 校验邮箱格式
     * @param email
     * @return boolean
     */
    public static boolean checkEmail(String email) {
        if (email != null && (!email.isEmpty())) {
            return Pattern
                    .matches("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email);
        }
        return true;
    }
}
