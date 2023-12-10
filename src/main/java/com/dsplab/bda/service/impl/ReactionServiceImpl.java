package com.dsplab.bda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Reaction;
import com.dsplab.bda.domain.vo.PageVo;
import com.dsplab.bda.domain.vo.ReactionVo;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.mapper.ReactionMapper;
import com.dsplab.bda.service.ReactionService;
import com.dsplab.bda.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("reactionService")
@Slf4j
public class ReactionServiceImpl extends ServiceImpl<ReactionMapper, Reaction> implements ReactionService {
    @Override
    public ResponseResult reactionList(Integer pageNum, Integer pageSize) {
        //参数非空校验
        if (Objects.isNull(pageNum) || Objects.isNull(pageSize)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        LambdaQueryWrapper<Reaction> wrapper = new LambdaQueryWrapper<>();

        //分页查询
        Page<Reaction> page = new Page<>(pageNum, pageSize);
        page(page,wrapper);
        List<Reaction> reactionList = page.getRecords();

        //封装vo
        List<ReactionVo> reactionVos = BeanCopyUtils.copyBeanList(reactionList, ReactionVo.class);

        PageVo pageVo = new PageVo(reactionVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
