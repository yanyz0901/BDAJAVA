package com.dsplab.bda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Compound;
import com.dsplab.bda.domain.entity.Task;
import com.dsplab.bda.domain.entity.User;
import com.dsplab.bda.domain.vo.CompoundVo;
import com.dsplab.bda.domain.vo.PageVo;
import com.dsplab.bda.domain.vo.TaskVo;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.enums.TaskStatusEnum;
import com.dsplab.bda.mapper.CompoundMapper;
import com.dsplab.bda.mapper.HostcellMapper;
import com.dsplab.bda.mapper.UserMapper;
import com.dsplab.bda.service.CompoundService;
import com.dsplab.bda.service.HostcellService;
import com.dsplab.bda.service.UserService;
import com.dsplab.bda.utils.BeanCopyUtils;
import com.dsplab.bda.utils.RestTemplateUtils;
import com.dsplab.bda.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service("compoundService")
@Slf4j
public class CompoundServiceImpl extends ServiceImpl<CompoundMapper, Compound> implements CompoundService {
    @Override
    public ResponseResult compoundList(Integer pageNum, Integer pageSize) {
        //参数非空校验
        if (Objects.isNull(pageNum) || Objects.isNull(pageSize)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        LambdaQueryWrapper<Compound> wrapper = new LambdaQueryWrapper<>();

        //分页查询
        Page<Compound> page = new Page<>(pageNum, pageSize);
        page(page,wrapper);
        List<Compound> compoundList = page.getRecords();

        //封装vo
        List<CompoundVo> compoundVos = BeanCopyUtils.copyBeanList(compoundList, CompoundVo.class);

        PageVo pageVo = new PageVo(compoundVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
