package com.dsplab.bda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Hostcell;
import com.dsplab.bda.domain.entity.Task;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.mapper.HostcellMapper;
import com.dsplab.bda.service.HostcellService;
import com.dsplab.bda.service.UserService;
import com.dsplab.bda.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * (Hostcell)表服务实现类
 *
 * @author makejava
 * @since 2023-12-03 14:46:29
 */
@Service("hostcellService")
@Slf4j
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

    @Override
    public ResponseResult deleteHostcell(Long id) {
        //参数非空校验
        if(Objects.isNull(id) || id <= 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        LambdaQueryWrapper<Hostcell> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Hostcell::getTaskId, id);

        //查询该用户是否是管理员，若否则加入过滤条件
        if(!userService.isAdmin()){
            wrapper.eq(Hostcell::getUserId, SecurityUtils.getUserId());
        }
        Hostcell hostcell = getOne(wrapper);
        if(Objects.isNull(hostcell)){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"用户对应任务列表中没有该条数据");
        }

        //逻辑删除
        if (SqlHelper.retBool(getBaseMapper().delete(wrapper))) {
            log.info("delete database success!");
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            log.error("delete database failed!");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

}
