package com.dsplab.bda.controller;

import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.User;
import com.dsplab.bda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PostMapping("/update")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @GetMapping("/userList")
    public ResponseResult userList(Integer pageNum,Integer pageSize){
        return userService.userList(pageNum, pageSize);
    }

    @PostMapping("/adminPermission")
    public ResponseResult modifyPermission(@RequestBody User user){
        return userService.modifyPermission(user);
    }

    @PostMapping("/banUser")
    public ResponseResult banUser(@RequestBody User user){
        return userService.banUser(user);
    }
}
