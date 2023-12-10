package com.dsplab.bda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Hostcell;

/**
 * (Hostcell)表服务接口
 *
 * @author makejava
 * @since 2023-12-03 14:46:29
 */
public interface HostcellService extends IService<Hostcell> {

    ResponseResult hostcellList();

    ResponseResult deleteHostcell(Long id);


}
