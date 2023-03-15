package com.dsplab.bda.enums;

public enum AppHttpCodeEnum {

    SUCCESS(200,"操作成功"),
    INPUT_NOT_NULL(400, "输入参数不能为空"),
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    PHONE_FORMAT_WRONG(405,"手机号格式错误"),
    EMAIL_FORMAT_WRONG(406, "邮箱格式错误"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONE_NUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    LOGIN_ERROR(504,"用户名或密码错误"),
    USERNAME_NOT_NULL(505, "用户名不能为空"),
    PASSWORD_NOT_NULL(506, "密码不能为空"),
    PHONE_NOT_NULL(507, "手机号不能为空"),
    EMAIL_NOT_NULL(508, "邮箱不能为空"),
    CONFIG_INFO_NOT_NULL(509, "配置信息不能为空"),
    USER_STATUS_BANNED(510, "用户被禁用"),
    USER_NOT_EXIST(511, "用户不存在");

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

