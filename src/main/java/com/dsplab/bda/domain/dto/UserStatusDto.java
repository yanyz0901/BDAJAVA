package com.dsplab.bda.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "修改用户状态dto")
public class UserStatusDto {
    //主键
    private Long id;
    //账号状态（0正常 1停用）
    private String status;
}
