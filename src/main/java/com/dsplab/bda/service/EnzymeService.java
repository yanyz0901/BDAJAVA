package com.dsplab.bda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Enzyme;

public interface EnzymeService extends IService<Enzyme> {
    ResponseResult enzymeList(Integer pageNum, Integer pageSize);
}
