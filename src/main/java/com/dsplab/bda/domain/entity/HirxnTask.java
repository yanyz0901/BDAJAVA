package com.dsplab.bda.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("hirxn_task")
public class HirxnTask {
    //任务id
    @TableId
    private String taskId;
    //反应表达式
    private String rxnSmiles;
    //半径
    private String radius;
    //预测任务类型
    private String taskType;
    //任务完成状态,任务运行完成时为1，其余为0
    private String status;
    //反应token结果
    private String rxnTokens;
    //预测值
    private String prediction;
    //图片ftp路径
    private String reactionImgName;
}
