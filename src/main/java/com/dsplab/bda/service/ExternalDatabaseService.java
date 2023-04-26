package com.dsplab.bda.service;

import com.dsplab.bda.domain.ResponseResult;

public interface ExternalDatabaseService {

    ResponseResult getKEGGList();

    ResponseResult getBiGGList();


    ResponseResult getKEGGInfoById(String keggId);

    ResponseResult getBiGGInfoById(String biggModelId);

    ResponseResult getBioCycInfoById(String biocycId);

}
