package com.dsplab.bda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Compound;

public interface CompoundService extends IService<Compound> {
    ResponseResult compoundList(Integer pageNum, Integer pageSize);
}
