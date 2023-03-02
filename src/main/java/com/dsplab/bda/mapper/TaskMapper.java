package com.dsplab.bda.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsplab.bda.domain.entity.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * (Task)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-06 11:28:57
 */
@Component
public interface TaskMapper extends BaseMapper<Task> {

}
