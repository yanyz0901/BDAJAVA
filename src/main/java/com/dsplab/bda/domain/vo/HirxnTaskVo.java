package com.dsplab.bda.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HirxnTaskVo {
    //任务id
    private String taskId;
    //任务完成状态,任务运行完成时为1，其余为0
    private String status;
    //反应token结果
    private List<String> rxnTokens;
    //预测值
    private String prediction;
    //图片ftp路径
    private String reactionImgName;
}
