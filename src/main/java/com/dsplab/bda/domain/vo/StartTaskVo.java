package com.dsplab.bda.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartTaskVo {
    // 以下是mooSeeker所需要的参数
    //1.task_id 任务id
    @JSONField(name = "task_id")
    private String taskId;
    //2.pop_size 种群大小
    @JSONField(name = "pop_size")
    private Integer popSize;
    //3.gen 迭代次数
    private Integer gen;
    //4.NrMax 路径最大长度
    @JSONField(name = "NrMax")
    private Integer nrMax;
    //5.ob_product 目标产物
    @JSONField(name = "ob_product")
    private String obProduct;
    //6.ob_substrate 目标底物
    @JSONField(name = "ob_substrate")
    private String ob_substrate;
    //7.abundant 初始化合物
    private String[] abundant;
    //8.database 数据库
    private String[] database;
    //9.host 宿主细胞
    private String[] host;
    //10.eva_func 评估函数
    @JSONField(name = "eva_func")
    private String[] evaFunc;
}
