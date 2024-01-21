package com.dsplab.bda.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "新增Hirxn任务dto")
public class AddHirxnTaskDto {
    //任务id
    private String taskId;
    //反应表达式
    private String rxnSmiles;
    //半径
    private String radius;
    //预测任务类型
    private String taskType;
}
