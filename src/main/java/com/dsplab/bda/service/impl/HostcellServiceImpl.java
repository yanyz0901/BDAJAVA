package com.dsplab.bda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Hostcell;
import com.dsplab.bda.mapper.HostcellMapper;
import com.dsplab.bda.service.HostcellService;
import com.dsplab.bda.service.UserService;
import com.dsplab.bda.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Hostcell)表服务实现类
 *
 * @author makejava
 * @since 2023-12-03 14:46:29
 */
@Service("hostcellService")
public class HostcellServiceImpl extends ServiceImpl<HostcellMapper, Hostcell> implements HostcellService {

    @Autowired
    private UserService userService;

    @Autowired
    private HostcellMapper hostcellMapper;

    @Override
    public ResponseResult hostcellList() {

        LambdaQueryWrapper<Hostcell> wrapper = new LambdaQueryWrapper<>();
        if(!userService.isAdmin()){
            wrapper.eq(Hostcell::getUserId, SecurityUtils.getUserId());
        }
        List<Hostcell> hostcells = hostcellMapper.selectList(wrapper);
        return ResponseResult.okResult(hostcells);
    }
}
