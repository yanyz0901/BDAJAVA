package com.dsplab.bda.controller;

import com.dsplab.bda.annotation.SystemLog;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.vo.FTPVO;
import com.dsplab.bda.service.FTPService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ftp")
@Api(tags = "文件管理api")
public class FTPController {

    @Autowired
    private FTPService ftpService;

    @PostMapping("/download")
    @SystemLog
    @ApiOperation(value = "下载文件", notes = "需要携带token")
    public ResponseResult downloadFile(@RequestBody FTPVO ftpvo){
        return ftpService.downloadFile(ftpvo);
    }

    @PostMapping("/upload")
    @SystemLog
    @ApiOperation(value = "上传文件", notes = "需要携带token")
    public ResponseResult uploadFile(@RequestBody FTPVO ftpvo){
        return ftpService.uploadFile(ftpvo);
    }

    @PostMapping("/delete")
    @SystemLog
    @ApiOperation(value = "删除文件", notes = "需要携带token")
    public ResponseResult deleteFile(@RequestBody FTPVO ftpvo){
        return ftpService.deleteFile(ftpvo);
    }
}
