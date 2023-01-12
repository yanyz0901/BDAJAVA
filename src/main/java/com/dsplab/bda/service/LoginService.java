package com.dsplab.bda.service;

import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.User;
import org.springframework.web.bind.annotation.ResponseBody;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
