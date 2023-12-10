package com.dsplab.bda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Reaction;

public interface ReactionService extends IService<Reaction> {
    ResponseResult reactionList(Integer pageNum, Integer pageSize);
}
