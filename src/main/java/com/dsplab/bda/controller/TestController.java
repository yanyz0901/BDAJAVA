package com.dsplab.bda.controller;

import com.dsplab.bda.annotation.SystemLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

//测试git-lht
@RestController
@Api(tags = "测试专用api")
public class TestController {
    @PutMapping("/ping")
    @SystemLog
    @ApiOperation("测试api")
    public String testConnection(){
        return "测试成功";
    }
}
