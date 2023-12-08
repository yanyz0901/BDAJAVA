package com.dsplab.bda.controller;

import com.dsplab.bda.annotation.SystemLog;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.service.HostcellService;
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
public class HostcellController {
    /**
     * 服务对象
     */
    @Resource
    private HostcellService hostcellService;

    @GetMapping("/getList")
//    @SystemLog
    public ResponseResult getHostcellList(){
        return hostcellService.hostcellList();
    }
}

