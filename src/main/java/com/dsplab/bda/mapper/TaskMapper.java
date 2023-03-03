package com.dsplab.bda.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsplab.bda.domain.entity.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * (Task)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-06 11:28:57
 */
public interface TaskMapper extends BaseMapper<Task> {

}
