package com.dsplab.bda.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "新增任务dto")
public class AddTaskDto {
    //任务配置详情
    private String configInfo;
}