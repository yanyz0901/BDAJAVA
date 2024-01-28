package com.dsplab.bda.controller;

import com.dsplab.bda.annotation.SystemLog;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.dto.AddHirxnTaskDto;
import com.dsplab.bda.domain.vo.HirxnTaskVo;
import com.dsplab.bda.service.impl.HirxnServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hirxn")
@Api(tags = "hirxn任务管理api")

public class HirxnController {

    @Autowired
    private HirxnServiceImpl hirxnService;

    @PostMapping("/submit")
    @SystemLog
    @ApiOperation(value = "新增hirxn任务", notes = "不需要携带token")
    public ResponseResult addHirxnTask(@RequestBody AddHirxnTaskDto addHirxnTaskDto){
        return hirxnService.submitHirxnTask(addHirxnTaskDto);
    }

    @PostMapping("/query/{taskId}")
    @SystemLog
    @ApiOperation(value = "查询hirxn任务", notes = "不需要携带token")
    public ResponseResult queryHirxnTask(@PathVariable String taskId){
        return hirxnService.queryTaskById(taskId);
    }

    @PostMapping("/result")
    @SystemLog
    @ApiOperation(value = "保存任务结果", notes = "不需要携带token")
    public ResponseResult addResult(@RequestBody HirxnTaskVo hirxnTaskVo){
        return hirxnService.updateResult(hirxnTaskVo);
    }
}
