package com.dsplab.bda.service;

import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.vo.FTPVO;

public interface FTPService{

    ResponseResult downloadFile(FTPVO ftpvo);

    ResponseResult uploadFile(FTPVO ftpvo);

    ResponseResult deleteFile(FTPVO ftpvo);
}
