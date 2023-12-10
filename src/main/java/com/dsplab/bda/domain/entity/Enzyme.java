package com.dsplab.bda.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bdaenzyme")

public class Enzyme {
    //反应id
    @TableId
    private Long id;
    private String entry;
    private String name;
    private String className;
    private String sysname;
    private String reaction;
    private String substrate;
    private String product;
    private String reference;
    private String otherDbs;
}

