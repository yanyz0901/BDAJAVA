package com.dsplab.bda.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionVo {
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
