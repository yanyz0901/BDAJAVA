package com.dsplab.bda.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnzymeVo {
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
