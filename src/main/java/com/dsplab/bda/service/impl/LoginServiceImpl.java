package com.dsplab.bda.service.impl;

import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.LoginUser;
import com.dsplab.bda.domain.entity.User;
import com.dsplab.bda.domain.vo.UserLoginVo;
import com.dsplab.bda.domain.vo.UserVo;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.enums.UserStatusEnum;
import com.dsplab.bda.service.LoginService;
import com.dsplab.bda.utils.BeanCopyUtils;
import com.dsplab.bda.utils.JwtUtil;
import com.dsplab.bda.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException(AppHttpCodeEnum.LOGIN_ERROR.getMsg());
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //查询用户状态是否正常
        if(UserStatusEnum.DISABLED.getStatusCode().equals(loginUser.getUser().getStatus())){
            throw new RuntimeException(AppHttpCodeEnum.USER_STATUS_BANNED.getMsg());
        }
        //获取userId 生成token
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);

        //把User转换成UserInfoVo
        UserVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserVo.class);
        UserLoginVo vo = new UserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取token 解析获取userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userId
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}
