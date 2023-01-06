package com.dsplab.bda.controller;

import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseResult getTaskInfoById(@PathVariable Long id) {
        return taskService.getTaskInfoById(id);
    }
}
