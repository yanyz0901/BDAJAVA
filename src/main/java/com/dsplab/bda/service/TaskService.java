package com.dsplab.bda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.dto.TaskListDto;
import com.dsplab.bda.domain.entity.Task;


/**
 * (Task)表服务接口
 *
 * @author makejava
 * @since 2023-01-06 11:28:57
 */
public interface TaskService extends IService<Task> {

    ResponseResult getTaskInfoById(Long id);

    ResponseResult taskList(Integer pageNum, Integer pageSize, TaskListDto taskListDto);

    ResponseResult addTask(Task task);

    ResponseResult updateTask(Task task);

    ResponseResult updateTaskResult(Task task);

    ResponseResult deleteTask(Long id);

    ResponseResult startTask(Integer id) ;

    ResponseResult getTaskResult(Integer id);

    ResponseResult startTaskByMq(Integer id);
}
