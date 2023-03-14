package com.dsplab.bda.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "条件查询用户列表dto")
public class UserListDto {
    //用户名
    private String userName;
    //用户类型
    private String type;
    //用户状态
    private String status;
    //用户ID
    private Long id;
}
