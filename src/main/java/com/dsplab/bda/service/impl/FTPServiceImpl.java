package com.dsplab.bda.service.impl;

import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.vo.FTPVO;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.service.FTPService;
import com.dsplab.bda.utils.FTPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class FTPServiceImpl implements FTPService {
    @Override
    public ResponseResult downloadFile(FTPVO ftpvo) {
        if(ftpvo.getFtpPath()==null||ftpvo.getLocalPath()==null){
            log.error("下载文件时源路径和目标路径均不能为空！");
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }
        String fileName = StringUtils.hasText(ftpvo.getFileName())?ftpvo.getFileName():null;
        try {
            FTPUtils.initFtpClient();
            boolean isSuccess = FTPUtils.downloadFile(ftpvo.getFtpPath(), fileName, ftpvo.getLocalPath());
            FTPUtils.dropFtpClient();
            if(isSuccess) return ResponseResult.okResult("文件下载成功！");
            else return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"文件下载失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"文件下载失败！");
        }
    }

    @Override
    public ResponseResult uploadFile(FTPVO ftpvo) {
        if(ftpvo.getFtpPath()==null||ftpvo.getLocalPath()==null){
            log.error("上传文件时源路径和目标路径均不能为空！");
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }
            boolean isSuccess = FTPUtils.uploadFile(ftpvo.getFtpPath(), ftpvo.getLocalPath());
            if(isSuccess) return ResponseResult.okResult("文件上传成功！");
            else return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"文件上传失败！");
    }

    @Override
    public ResponseResult deleteFile(FTPVO ftpvo) {
        if(ftpvo.getFtpPath()==null){
            log.error("下载文件时文件路径不能为空！");
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }
        String fileName = StringUtils.hasText(ftpvo.getFileName())?ftpvo.getFileName():null;
        boolean isSuccess = FTPUtils.deleteFile(ftpvo.getFtpPath(), fileName);
        if(isSuccess) return ResponseResult.okResult("文件删除成功！");
        else return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"文件删除失败！");
    }
}
