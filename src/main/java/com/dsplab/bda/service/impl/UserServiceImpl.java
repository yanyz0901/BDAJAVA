package com.dsplab.bda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.User;
import com.dsplab.bda.domain.vo.PageVo;
import com.dsplab.bda.domain.vo.UserVo;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.exception.SystemException;
import com.dsplab.bda.mapper.UserMapper;
import com.dsplab.bda.service.UserService;
import com.dsplab.bda.utils.BeanCopyUtils;
import com.dsplab.bda.utils.SecurityUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-01-06 11:29:30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPhone())){
            throw new SystemException(AppHttpCodeEnum.PHONE_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if(phoneExist(user.getPhone())){
            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
        }
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public Boolean isAdmin() {
        User user = getById(SecurityUtils.getUserId());
        return "1".equals(user.getType());
    }

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserVo vo = BeanCopyUtils.copyBean(user,UserVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        //更新用户名 检查是否重复
        if(Strings.hasText(user.getUserName())){
            if(userNameExist(user.getUserName())){
                throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
            }
        }
        //更新手机号 检查是否重复
        if(Strings.hasText(user.getPhone())){
            if(phoneExist(user.getPhone())){
                throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
            }
        }
        //更新邮箱 检查是否重复
        if(Strings.hasText(user.getEmail())){
            if(emailExist(user.getEmail())){
                throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
            }
        }
        //更新密码 密码加密
        if(Strings.hasText(user.getPassword())){
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
        }
        //id兜底
        if(Objects.nonNull(user.getId()) && user.getId()!=SecurityUtils.getUserId() && !isAdmin()){
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        if(Objects.isNull(user.getId())){
            user.setId(SecurityUtils.getUserId());
        }
        //更新数据库
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userList(Integer pageNum, Integer pageSize) {
        //判断用户是否为管理员
        if(!isAdmin()){
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        //参数非空校验
        if (Objects.isNull(pageNum) || Objects.isNull(pageSize)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }
        //分页查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStatus,"0");
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page,wrapper);
        List<User> userList = page.getRecords();
        //封装vo
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(userList, UserVo.class);
        PageVo pageVo = new PageVo(userVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult modifyPermission(User user) {
        //判断用户是否为管理员
        if(!isAdmin()){
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        //输入参数校验
        if(Objects.isNull(user.getId()) || Objects.isNull(user.getType())){
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        User u = getById(user.getId());
        if(Objects.isNull(u)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS.getCode(), "用户不存在！");
        }
        //更新数据库
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult banUser(User user) {
        //判断用户是否为管理员
        if(!isAdmin()){
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        //输入参数校验
        if(Objects.isNull(user.getId()) || Objects.isNull(user.getStatus())){
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        User u = getById(user.getId());
        if(Objects.isNull(u)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS.getCode(), "用户不存在！");
        }
        //更新数据库
        updateById(user);
        return ResponseResult.okResult();
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }
    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper)>0;
    }
    private boolean phoneExist(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        return count(queryWrapper)>0;
    }
}
