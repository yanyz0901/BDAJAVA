package com.dsplab.bda.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dsplab.bda.config.RabbitmqConfig;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.dto.AddHirxnTaskDto;
import com.dsplab.bda.domain.entity.HirxnTask;
import com.dsplab.bda.domain.vo.HirxnTaskVo;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.mapper.HirxnTaskMapper;
import com.dsplab.bda.service.HirxnService;
import com.dsplab.bda.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.UUID;

import java.util.Objects;

@Service("hirxnService")
@Slf4j
public class HirxnServiceImpl extends ServiceImpl<HirxnTaskMapper, HirxnTask> implements HirxnService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public ResponseResult submitHirxnTask(AddHirxnTaskDto addHirxnTaskDto) {
        //输入参数校验
        if(!verifyInput(addHirxnTaskDto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }
        //将hirxn任务存储至数据库
        String taskId = UUID.randomUUID().toString().replaceAll("-", "");
        addHirxnTaskDto.setTaskId(taskId);
        HirxnTask hirxnTask = BeanCopyUtils.copyBean(addHirxnTaskDto, HirxnTask.class);
        hirxnTask.setStatus("0");
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        try{
            if (save(hirxnTask)) {
                log.info("write database success!");
                //将任务投放至消息队列
                rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TASK, RabbitmqConfig.ROUTING_KEY_HIRXN, JSON.toJSONString(addHirxnTaskDto, serializeConfig));
                //返回任务id
                return ResponseResult.okResult(taskId);
            } else {
                log.error("write database failed!");
            }
        }catch (Exception e){
            log.error("write database failed!", e);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult queryTaskById(String taskId) {
        //参数非空校验
        if (StringUtils.isEmpty(taskId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        LambdaQueryWrapper<HirxnTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HirxnTask::getTaskId, taskId);
        HirxnTask hirxnTask = getOne(wrapper);
        if(Objects.isNull(hirxnTask)){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "未查询到结果");
        }
        HirxnTaskVo hirxnTaskVo = BeanCopyUtils.copyBean(hirxnTask, HirxnTaskVo.class);
        hirxnTaskVo.setRxnTokens(JSON.parseArray(hirxnTask.getRxnTokens(),String.class));
        return ResponseResult.okResult(hirxnTaskVo);
    }

    @Override
    public ResponseResult updateResult(HirxnTaskVo hirxnTaskVo) {
        //参数非空校验
        if (Objects.isNull(hirxnTaskVo.getTaskId())) {
            log.error("not input taskId");
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }
        LambdaQueryWrapper<HirxnTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HirxnTask::getTaskId, hirxnTaskVo.getTaskId());
        HirxnTask hirxnTask = getOne(wrapper);
        if(Objects.isNull(hirxnTask)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"hirxn任务列表中没有该条数据");
        }
        // 更新hirxn结果
        hirxnTask.setStatus("1");
        hirxnTask.setPrediction(hirxnTaskVo.getPrediction());
        hirxnTask.setRxnTokens(JSON.toJSONString(hirxnTaskVo.getRxnTokens()));
        hirxnTask.setReactionImgName(hirxnTaskVo.getReactionImgName());
        // 将hirxn结果存入数据库
        if (update(hirxnTask, wrapper)) {
            log.info("update database success!");
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            log.error("update database failed!");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 校验hirxn的输入参数
     * @param addHirxnTaskDto
     * @return
     */
    private boolean verifyInput(AddHirxnTaskDto addHirxnTaskDto) {

        if(!"0".equals(addHirxnTaskDto.getRadius()) && !"1".equals(addHirxnTaskDto.getRadius()) && !"2".equals(addHirxnTaskDto.getRadius())){
            return false;
        }
        if(!"classification".equals(addHirxnTaskDto.getTaskType()) && !"regression".equals(addHirxnTaskDto.getTaskType())){
            return false;
        }
        if(StringUtils.isEmpty(addHirxnTaskDto.getRxnSmiles()) || addHirxnTaskDto.getRxnSmiles().length() > 5000){
            return false;
        }
        if(!addHirxnTaskDto.getRxnSmiles().contains(">>")){
            return false;
        }
        return true;
    }
}
