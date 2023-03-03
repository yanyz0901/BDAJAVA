package com.dsplab.bda.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartTaskVo {
    //以下是mooseeker所需要的参数
    //1.task_id
    private Integer task_id;
    //2.populationsize
    private Integer populationsize;
    //3.iterations
    private Integer iterations;
    //4.maximumlength
    private Integer maximumlength;
    //5.targetcompound
    private String targertcompound;
    //6.initialcompound
    private String[] initialcompound;
    //7.dataset
    private String[] dataset;
    //8.hostcell
    private String[] hostcell;
    //9.evaluation
    private String[] evaluation;
}
