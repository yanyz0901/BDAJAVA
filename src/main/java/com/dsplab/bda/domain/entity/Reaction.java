package com.dsplab.bda.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Reaction)表实体类
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bdareactions")

public class Reaction {
    //反应id
    @TableId
    private String id;
    private String name;
    private String definition;
    private String chebi;
    private String isBalanced;
    private String isTransport;
    private Float dgMean;
    private Float dgStd;
    private String equation;
    private String enzyme;
    private String reference;
    private String idFromReference;
}
