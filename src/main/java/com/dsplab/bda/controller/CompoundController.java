package com.dsplab.bda.controller;

import com.dsplab.bda.annotation.SystemLog;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.dto.TaskListDto;
import com.dsplab.bda.service.CompoundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compound")
@Api(tags = "化合物管理api")
public class CompoundController {
    @Autowired
    private CompoundService compoundService;

    @GetMapping("/compoundList")
    @SystemLog
    @ApiOperation(value = "分页查询化合物列表", notes = "需要携带token，管理员可查询全部任务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "页大小")
    })
    public ResponseResult compoundList(Integer pageNum, Integer pageSize){
        return compoundService.compoundList(pageNum, pageSize);
    }
}
