package com.dsplab.bda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Task;


/**
 * (Task)表服务接口
 *
 * @author makejava
 * @since 2023-01-06 11:28:57
 */
public interface TaskService extends IService<Task> {

    ResponseResult getTaskInfoById(Long id);
}
