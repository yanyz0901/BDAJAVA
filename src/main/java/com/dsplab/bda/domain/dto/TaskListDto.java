package com.dsplab.bda.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "条件查询任务列表dto")
public class TaskListDto {
    //用户名
    private String userName;
    //任务类型
    private String taskType;
    //任务状态
    private String status;
    //任务ID
    private Long taskId;
}
