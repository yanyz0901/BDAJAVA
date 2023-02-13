package com.dsplab.bda.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "修改用户权限dto")
public class UserTypeDto {
    //主键
    private Long id;
    //用户类型：0代表普通用户，1代表管理员
    private String type;
}
