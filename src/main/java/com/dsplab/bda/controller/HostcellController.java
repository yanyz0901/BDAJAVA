package com.dsplab.bda.controller;

import com.dsplab.bda.annotation.SystemLog;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.service.HostcellService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Hostcell)表控制层
 *
 * @author makejava
 * @since 2023-12-03 14:46:13
 */
@RestController
@RequestMapping("/hostcell")
@Api(tags = "底盘制作api")
public class HostcellController {
    /**
     * 服务对象
     */
    @Resource
    private HostcellService hostcellService;

    @GetMapping("/getList")
    @SystemLog
    @ApiOperation(value = "获取用户下的底盘列表", notes = "需要携带token")
    public ResponseResult getHostcellList(){
        return hostcellService.hostcellList();
    }
}

