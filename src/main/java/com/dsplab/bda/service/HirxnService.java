package com.dsplab.bda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.dto.AddHirxnTaskDto;
import com.dsplab.bda.domain.entity.HirxnTask;
import com.dsplab.bda.domain.vo.HirxnTaskVo;

public interface HirxnService extends IService<HirxnTask> {

    ResponseResult submitHirxnTask(AddHirxnTaskDto addHirxnTaskDto);

    ResponseResult queryTaskById(String taskId);

    ResponseResult updateResult(HirxnTaskVo hirxnTaskVo);
}
