package com.dsplab.bda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.dto.UserListDto;
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

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult userList(Integer pageNum, Integer pageSize, UserListDto userListDto);

    ResponseResult modifyPermission(User user);

    ResponseResult banUser(User user);

    User getUserByName(String name);

    ResponseResult getUserRole();
}
