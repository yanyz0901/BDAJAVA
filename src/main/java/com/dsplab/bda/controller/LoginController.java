package com.dsplab.bda.controller;

import com.dsplab.bda.annotation.SystemLog;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.dto.LoginDto;
import com.dsplab.bda.domain.entity.User;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.exception.SystemException;
import com.dsplab.bda.service.LoginService;
import com.dsplab.bda.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户登录api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @SystemLog
    @ApiOperation(value = "登录用户", notes = "请求体中需要传入用户名和密码")
    public ResponseResult login(@RequestBody LoginDto loginDto){
        User user = BeanCopyUtils.copyBean(loginDto, User.class);
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        return loginService.login(user);
    }

    @PostMapping("/logout")
    @SystemLog
    @ApiOperation(value = "登出用户", notes = "需要携带token")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
