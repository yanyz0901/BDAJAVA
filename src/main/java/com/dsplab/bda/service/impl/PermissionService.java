package com.dsplab.bda.service.impl;

import com.dsplab.bda.service.UserService;
import com.dsplab.bda.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {

    @Autowired
    private UserService userService;
    /**
     * 判断当前用户是否具有permission
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermission(String permission){
        //如果是超级管理员 直接返回true
        if(userService.isAdmin()){
            return true;
        }
        //否则获取当前登录用户所具有的权限列表 如何判断是否存在permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
