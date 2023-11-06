package com.dsplab.bda.domain.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "文件管理vo")
public class FTPVO {
    //ftp路径(相对路径)
    private String ftpPath;
    //本地路径（绝对路径）
    private String localPath;
    //文件名（仅在删除指定文件的方法中使用）
    private String fileName;
}
