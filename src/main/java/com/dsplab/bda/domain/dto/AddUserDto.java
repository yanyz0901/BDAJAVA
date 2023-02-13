package com.dsplab.bda.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "新增用户dto")
public class AddUserDto {
    //用户名
    private String userName;
    //密码
    private String password;
    //邮箱
    private String email;
    //手机
    private String phone;
}
