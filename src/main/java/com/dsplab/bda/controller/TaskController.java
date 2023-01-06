package com.dsplab.bda.controller;

import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Task;
import com.dsplab.bda.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseResult getTaskInfoById(@PathVariable Long id) {
        return taskService.getTaskInfoById(id);
    }

    @GetMapping("/taskList")
    public ResponseResult taskList(Integer pageNum,Integer pageSize){
        return taskService.taskList(pageNum,pageSize);
    }

    @PostMapping("/addTask")
    public ResponseResult addTask(@RequestBody Task task){
        return taskService.addTask(task);
    }

    @PostMapping("/updateTask")
    public ResponseResult updateTask(@RequestBody Task task){
        return taskService.updateTask(task);
    }

    @GetMapping("/deleteTask/{id}")
    public ResponseResult deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id);
    }
}
