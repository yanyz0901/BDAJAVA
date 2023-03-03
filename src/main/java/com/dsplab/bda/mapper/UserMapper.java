package com.dsplab.bda.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsplab.bda.domain.entity.User;
import org.springframework.stereotype.Repository;


/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-06 11:29:30
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
