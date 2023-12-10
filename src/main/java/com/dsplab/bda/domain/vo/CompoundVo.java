package com.dsplab.bda.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompoundVo {
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
