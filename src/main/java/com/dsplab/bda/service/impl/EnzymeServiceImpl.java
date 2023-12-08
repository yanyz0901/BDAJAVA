package com.dsplab.bda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Compound;
import com.dsplab.bda.domain.entity.Enzyme;
import com.dsplab.bda.domain.entity.Reaction;
import com.dsplab.bda.domain.vo.EnzymeVo;
import com.dsplab.bda.domain.vo.PageVo;
import com.dsplab.bda.domain.vo.ReactionVo;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.mapper.CompoundMapper;
import com.dsplab.bda.mapper.EnzymeMapper;
import com.dsplab.bda.service.CompoundService;
import com.dsplab.bda.service.EnzymeService;
import com.dsplab.bda.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("enzymeService")
@Slf4j
public class EnzymeServiceImpl extends ServiceImpl<EnzymeMapper, Enzyme> implements EnzymeService {
    @Override
    public ResponseResult enzymeList(Integer pageNum, Integer pageSize) {
        //参数非空校验
        if (Objects.isNull(pageNum) || Objects.isNull(pageSize)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        LambdaQueryWrapper<Enzyme> wrapper = new LambdaQueryWrapper<>();

        //分页查询
        Page<Enzyme> page = new Page<>(pageNum, pageSize);
        page(page,wrapper);
        List<Enzyme> enzymeList = page.getRecords();

        //封装vo
        List<EnzymeVo> enzymeVos = BeanCopyUtils.copyBeanList(enzymeList, EnzymeVo.class);

        PageVo pageVo = new PageVo(enzymeVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
