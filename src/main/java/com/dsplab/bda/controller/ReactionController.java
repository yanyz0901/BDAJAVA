package com.dsplab.bda.controller;

import com.dsplab.bda.annotation.SystemLog;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.service.ReactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reaction")
@Api(tags = "反应管理api")
public class ReactionController {
    @Autowired
    private ReactionService reactionService;

    @GetMapping("/reactionList")
    @SystemLog
    @ApiOperation(value = "分页查询反应列表", notes = "需要携带token，管理员可查询全部任务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "页大小")
    })
    public ResponseResult reactionList(Integer pageNum, Integer pageSize){
        return reactionService.reactionList(pageNum, pageSize);
    }
}
