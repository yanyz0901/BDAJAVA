package com.dsplab.bda.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    //用户id
    private Long id;
    //用户名
    private String userName;
    //用户类型
    private String type;
    //备注
    private Boolean isAdmin;
}
