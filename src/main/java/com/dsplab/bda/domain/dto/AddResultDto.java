package com.dsplab.bda.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "保存结果dto")
public class AddResultDto {
    //任务id
    private Long taskId;
    //任务结果
    private String result;

}
