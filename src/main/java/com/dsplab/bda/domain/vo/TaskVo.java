package com.dsplab.bda.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskVo {
    //任务id
    private Long taskId;
    //创建人id
    private Long userId;
    //创建人姓名
    private String userName;
    //任务创建时间
    private Date createTime;
    //任务状态(0未开始 1进行中 2已完成)
    private String status;
    //任务配置详情
    private String configInfo;
    //任务结果
    private String result;
}
