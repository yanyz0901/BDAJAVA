package com.dsplab.bda.controller;

import com.dsplab.bda.annotation.SystemLog;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.dto.*;
import com.dsplab.bda.domain.entity.User;
import com.dsplab.bda.service.UserService;
import com.dsplab.bda.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @SystemLog
    @ApiOperation(value = "用户注册", notes = "不需要token")
    public ResponseResult register(@RequestBody AddUserDto addUserDto){
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        return userService.register(user);
    }

    @GetMapping("/userInfo")
    @SystemLog
    @ApiOperation(value = "获取用户详情", notes = "需要token")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PostMapping("/update")
    @SystemLog
    @ApiOperation(value = "修改用户名、密码、手机、邮箱", notes = "需要token，修改其他用户信息时需要管理员权限且携带id信息")
    public ResponseResult updateUserInfo(@RequestBody UpdateUserDto updateUserDto){
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        return userService.updateUserInfo(user);
    }

    @GetMapping("/userList")
    @PreAuthorize("@ps.hasPermission('管理员')")
    @SystemLog
    @ApiOperation(value = "获取用户列表", notes = "需要token，需要有管理员权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "页大小")
    })
    public ResponseResult userList(Integer pageNum,Integer pageSize, UserListDto userListDto){
        return userService.userList(pageNum, pageSize, userListDto);
    }

    @PostMapping("/adminPermission")
    @PreAuthorize("@ps.hasPermission('管理员')")
    @SystemLog
    @ApiOperation(value = "修改用户权限", notes = "需要token，需要有管理员权限，0为普通用户，1为管理员")
    public ResponseResult modifyPermission(@RequestBody UserTypeDto userTypeDto){
        User user = BeanCopyUtils.copyBean(userTypeDto, User.class);
        return userService.modifyPermission(user);
    }

    @PostMapping("/banUser")
    @PreAuthorize("@ps.hasPermission('管理员')")
    @SystemLog
    @ApiOperation(value = "修改用户状态", notes = "需要token，需要有管理员权限，0为正常，1为禁用")
    public ResponseResult banUser(@RequestBody UserStatusDto userStatusDto){
        User user = BeanCopyUtils.copyBean(userStatusDto, User.class);
        return userService.banUser(user);
    }

    @GetMapping("/getRole")
    @SystemLog
    @ApiOperation(value = "获得用户角色信息")
    public ResponseResult getRole(){
        return userService.getUserRole();
    }
}
