package com.dsplab.bda.utils;

import com.dsplab.bda.domain.entity.LoginUser;
import com.dsplab.bda.domain.entity.User;
import com.dsplab.bda.mapper.UserMapper;
import com.dsplab.bda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }


    /**
     * 获取用户id
     * @return
     */
    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
