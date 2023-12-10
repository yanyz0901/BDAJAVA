package com.dsplab.bda.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Compound)表实体类
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bdacompounds")
public class Compound {
    //化合物id
    @TableId
    private String id;
    private String name;
    private String formula;
    private Float charge;
    private Float mass;
    private String pubchem;
    private String chebi;
    private String inchi;
    private String inchiKey;
    private String reaction;
    private String enzyme;
    private String smiles;
    private String reference;
    private String idFromReference;
}
