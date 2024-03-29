package com.dsplab.bda.controller;

import com.alibaba.fastjson.JSONObject;
import com.dsplab.bda.annotation.SystemLog;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.dto.AddResultDto;
import com.dsplab.bda.domain.dto.AddTaskDto;
import com.dsplab.bda.domain.dto.TaskListDto;
import com.dsplab.bda.domain.dto.UpdateTaskDto;
import com.dsplab.bda.domain.entity.Task;
import com.dsplab.bda.service.TaskService;
import com.dsplab.bda.utils.BeanCopyUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@Api(tags = "任务管理api")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    @SystemLog
    @ApiOperation(value = "根据任务id获取任务信息", notes = "需要携带token")
    @ApiImplicitParam(name = "id", value = "任务id")
    public ResponseResult getTaskInfoById(@PathVariable Long id) {
        return taskService.getTaskInfoById(id);
    }

    @GetMapping("/taskList")
    @SystemLog
    @ApiOperation(value = "分页查询任务列表", notes = "需要携带token，管理员可查询全部任务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "页大小")
    })
    public ResponseResult taskList(Integer pageNum,Integer pageSize, TaskListDto taskListDto){
        return taskService.taskList(pageNum, pageSize, taskListDto);
    }

    @PostMapping("/addTask/{type}")
    @SystemLog
    @ApiOperation(value = "新增任务", notes = "需要携带token")
    public ResponseResult addTask(@RequestBody AddTaskDto addTaskDto, @PathVariable String type){
        Task task = BeanCopyUtils.copyBean(addTaskDto, Task.class);
        task.setTaskType(type);
        return taskService.addTask(task);
    }

    @PostMapping("/updateTask")
    @SystemLog
    @ApiOperation(value = "修改任务", notes = "需要携带token")
    public ResponseResult updateTask(@RequestBody UpdateTaskDto updateTaskDto){
        Task task = BeanCopyUtils.copyBean(updateTaskDto, Task.class);
        return taskService.updateTask(task);
    }

    @GetMapping("/deleteTask/{id}")
    @SystemLog
    @ApiOperation(value = "根据任务id删除任务", notes = "需要携带token")
    @ApiImplicitParam(name = "id", value = "任务id")
    public ResponseResult deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id);
    }

    @PostMapping("/result")
    @SystemLog
    @ApiOperation(value = "保存任务结果", notes = "不需要携带token")
    public ResponseResult addResult(@RequestBody JSONObject jsonObject){
        AddResultDto addResultDto = new AddResultDto(jsonObject.getLongValue("task_id"),jsonObject.get("result").toString());
        Task task = BeanCopyUtils.copyBean(addResultDto, Task.class);
        return taskService.updateTaskResult(task);
    }

    @GetMapping("/startTask/{id}")
    @SystemLog
    @ApiOperation(value = "根据任务id开始任务", notes = "需要携带token")
    @ApiImplicitParam(name = "id", value = "任务id")
    public ResponseResult startTask(@PathVariable Integer id) {
        return taskService.startTaskByMq(id);
    }

    @GetMapping("/getResultById/{id}")
    @SystemLog
    @ApiOperation(value = "根据任务id获取任务结果", notes = "需要携带token")
    @ApiImplicitParam(name = "id", value = "任务id")
    public ResponseResult getResultById(@PathVariable Integer id){
        return taskService.getTaskResult(id);
    }
}
