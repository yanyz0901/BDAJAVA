package com.dsplab.bda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.User;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-01-06 11:29:30
 */
public interface UserService extends IService<User> {

    ResponseResult register(User user);
    Boolean isAdmin();
}
