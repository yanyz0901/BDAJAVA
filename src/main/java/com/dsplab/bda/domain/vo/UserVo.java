package com.dsplab.bda.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private Long id;
    //用户名
    private String userName;
    //邮箱
    private String email;
    //手机
    private String phone;

}
