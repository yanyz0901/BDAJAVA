package com.dsplab.bda.controller;

import com.dsplab.bda.annotation.SystemLog;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.service.EnzymeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enzyme")
@Api(tags = "酶管理api")
public class EnzymeController {
    @Autowired
    private EnzymeService enzymeService;

    @GetMapping("/enzymeList")
    @SystemLog
    @ApiOperation(value = "分页查询酶列表", notes = "需要携带token，管理员可查询全部任务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "页大小")
    })
    public ResponseResult enzymeList(Integer pageNum, Integer pageSize){
        return enzymeService.enzymeList(pageNum, pageSize);
    }

}
