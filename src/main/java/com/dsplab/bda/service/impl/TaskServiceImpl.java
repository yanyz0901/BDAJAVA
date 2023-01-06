package com.dsplab.bda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Task;
import com.dsplab.bda.mapper.TaskMapper;
import com.dsplab.bda.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Task)表服务实现类
 *
 * @author makejava
 * @since 2023-01-06 11:28:58
 */
@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    public ResponseResult getTaskInfoById(Long id) {
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getTaskId, id);
        Task task = getOne(wrapper);
        return ResponseResult.okResult(task);
    }
}
