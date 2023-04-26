package com.dsplab.bda.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.dsplab.bda.config.RabbitmqConfig;
import com.dsplab.bda.constants.SystemConstants;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.dto.TaskListDto;
import com.dsplab.bda.domain.entity.Task;
import com.dsplab.bda.domain.entity.User;
import com.dsplab.bda.domain.vo.*;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.enums.TaskStatusEnum;
import com.dsplab.bda.mapper.TaskMapper;
import com.dsplab.bda.mapper.UserMapper;
import com.dsplab.bda.service.TaskService;
import com.dsplab.bda.service.UserService;
import com.dsplab.bda.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * (Task)表服务实现类
 *
 * @author makejava
 * @since 2023-01-06 11:28:58
 */
@Service("taskService")
@Slf4j
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private UserService userService;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public ResponseResult getTaskInfoById(Long id) {
        //参数非空校验
        if (Objects.isNull(id) || id <= 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        //根据taskId查询数据库
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getTaskId, id);

        //查询该用户是否是管理员，若否则加入过滤条件
        if(!userService.isAdmin()){
            wrapper.eq(Task::getUserId, SecurityUtils.getUserId());
        }

        //封装为vo
        Task task = getOne(wrapper);
        TaskVo taskVo = BeanCopyUtils.copyBean(task, TaskVo.class);
        taskVo.setUserName(userService.getById(SecurityUtils.getUserId()).getUserName());
        taskVo.setStatus(TaskStatusEnum.getByCode(task.getStatus()));

        if (Objects.nonNull(task)) return ResponseResult.okResult(taskVo);
        else {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "未查询到结果");
        }
    }

    @Override
    public ResponseResult taskList(Integer pageNum, Integer pageSize, TaskListDto taskListDto) {
        //参数非空校验
        if (Objects.isNull(pageNum) || Objects.isNull(pageSize)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        //查询该用户是否是管理员，若否则加入过滤条件
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        if(!userService.isAdmin()){
            wrapper.eq(Task::getUserId, SecurityUtils.getUserId());
        }
        //条件查询
        wrapper.eq(StringUtils.hasText(taskListDto.getStatus()), Task::getStatus, TaskStatusEnum.getByMsg(taskListDto.getStatus()));
        wrapper.eq(StringUtils.hasText(taskListDto.getTaskType()), Task::getTaskType, taskListDto.getTaskType());
        wrapper.eq(Objects.nonNull(taskListDto.getTaskId()), Task::getTaskId, taskListDto.getTaskId());
        wrapper.orderByDesc(Task::getCreateTime);
        if(StringUtils.hasText(taskListDto.getUserName())){
            User userByName = userService.getUserByName(taskListDto.getUserName());
            Long userId = (userByName == null? 0L:userByName.getId());
            wrapper.eq(Task::getUserId, userId);
        }
        //分页查询
        Page<Task> page = new Page<>(pageNum, pageSize);
        page(page,wrapper);
        List<Task> taskList = page.getRecords();

        //封装vo
        List<TaskVo> taskVos = BeanCopyUtils.copyBeanList(taskList, TaskVo.class);
        for (TaskVo taskVo : taskVos) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getId,taskVo.getUserId());
            User user = userMapper.selectOne(queryWrapper);
            taskVo.setUserName(user.getUserName());
            taskVo.setStatus(TaskStatusEnum.getByCode(taskVo.getStatus()));
        }
        PageVo pageVo = new PageVo(taskVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTask(Task task) {
        //参数非空校验
        if(Objects.isNull(task.getConfigInfo())){
            return ResponseResult.errorResult(AppHttpCodeEnum.CONFIG_INFO_NOT_NULL);
        }
        //从securityContext中获取userId
        Long userId = SecurityUtils.getUserId();
        task.setUserId(userId);
        //存入数据库
        if (save(task)) {
            log.info("write database success!");
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            log.error("write database failed!");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult updateTask(Task task) {
        //参数非空校验
        if (Objects.isNull(task.getTaskId())) {
            log.error("not input taskId");
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getTaskId, task.getTaskId());

        //查询该用户是否是管理员，若否则加入过滤条件
        if(!userService.isAdmin()){
            wrapper.eq(Task::getUserId, SecurityUtils.getUserId());
        }
        Task t = getOne(wrapper);
        if(Objects.isNull(t)){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"用户对应任务列表中没有该条数据");
        }

        if (update(task, wrapper)) {
            log.info("update database success!");
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            log.error("update database failed!");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult updateTaskResult(Task task) {
        //参数非空校验
        if (Objects.isNull(task.getTaskId())) {
            log.error("not input taskId");
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getTaskId, task.getTaskId());
        Task t = getOne(wrapper);
        if(Objects.isNull(t)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"任务列表中没有该条数据");
        }

        //根据userId找到User表中对应用户
        LambdaQueryWrapper<User> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(User::getId, t.getUserId());
        User user = userMapper.selectOne(wrapper1);
        if(Objects.isNull(user)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"找不到该用户");
        }
        task.setCompleteTime(new Date());
        task.setStatus(TaskStatusEnum.COMPLETED.getStatusCode());
        //算法端直接调用不需要登录检查
        if (update(task, wrapper)) {
            log.info("update database success!");
            String text = user.getUserName()+"您好，您参数配置为"+t.getConfigInfo()+"的任务执行结束，执行结果为"
                    +task.getResult()+"，任务详情请访问BDA网址。";
            MailVo mailVo = new MailVo(user.getEmail(), SystemConstants.EMAIL_NAME, text);
            mailService.sendMail(mailVo); //发送邮件
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            log.error("update database failed!");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult deleteTask(Long id) {
        //参数非空校验
        if(Objects.isNull(id) || id <= 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getTaskId, id);

        //查询该用户是否是管理员，若否则加入过滤条件
        if(!userService.isAdmin()){
            wrapper.eq(Task::getUserId, SecurityUtils.getUserId());
        }
        Task task = getOne(wrapper);
        if(Objects.isNull(task)){
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

    //开始任务api
    @Override
    public ResponseResult startTask(Integer id) {
        //参数非空校验
        if(Objects.isNull(id) || id <= 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }
        //根据输入的任务id找到对应任务
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getTaskId, id);
        Task task = getOne(wrapper);
        // 判断task是否存在
        if(Objects.isNull(task)){
            log.info("do not has the task");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"当前任务不存在");
        }
        //判断的任务中user_id，与当前的用户的id是否一致，不一致则报错
        if(!userService.isAdmin()&&!task.getUserId().equals(SecurityUtils.getUserId())){
            log.info("the user_id in the task are not match the now user");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"当前用户id与该任务中的用户id不符");
        }
        //根据id从数据库中获取任务信息
        String TaskInfo = task.getConfigInfo();
        //增加json中字段
        JSONObject taskVo = JSON.parseObject(TaskInfo, JSONObject.class);
        taskVo.put("task_id", id.toString());
        String json = taskVo.toJSONString();
        //发送给算法api
        JSONObject result= restTemplateUtils.doPostForObject(SystemConstants.MOOSEEKER_TASK_URL,json);
        log.info(result.toJSONString());

        if("200".equals(result.get("code").toString())){
            // 更新任务开始时间
            task.setStartTime(new Date());
            task.setStatus(TaskStatusEnum.STARTING.getStatusCode());
            update(task, wrapper);
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        //如果响应码不为200，则控制台输出
        log.error(result.toJSONString());//返回算法的响应
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"算法启动失败");
    }

    @Override
    public ResponseResult startTaskByMq(Integer id) {
        //参数非空校验
        if(Objects.isNull(id) || id <= 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }
        //根据输入的任务id找到对应任务
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getTaskId, id);
        Task task = getOne(wrapper);
        // 判断task是否存在
        if(Objects.isNull(task)){
            log.info("do not has the task");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"当前任务不存在");
        }
        //判断的任务中user_id，与当前的用户的id是否一致，不一致则报错
        if(!userService.isAdmin()&&!task.getUserId().equals(SecurityUtils.getUserId())){
            log.info("the user_id in the task are not match the now user");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"当前用户id与该任务中的用户id不符");
        }
        //根据id从数据库中获取任务信息
        String TaskInfo = task.getConfigInfo();
        JSONObject taskVo = JSON.parseObject(TaskInfo, JSONObject.class);
        taskVo.put("task_id", id.toString());
        log.info("配置信息为：", taskVo);
        switch (task.getTaskType()){
            case SystemConstants.MOO_SEEKER:
                rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TASK, RabbitmqConfig.ROUTING_KEY_MOO_SEEKER, taskVo.toJSONString());
                break;
            case SystemConstants.TOXICITY_PREDICTOR:
                rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TASK, RabbitmqConfig.ROUTING_KEY_TOXICITY_PREDICTOR, taskVo.toJSONString());
                break;
            case SystemConstants.YIELDS_CALCULATER:
                rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TASK, RabbitmqConfig.ROUTING_KEY_YIELDS_CALCULATER, taskVo.toJSONString());
                break;
            default:
                return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "任务类型错误");
        }
        // 更新任务开始时间
        task.setStartTime(new Date());
        task.setStatus(TaskStatusEnum.STARTING.getStatusCode());
        update(task, wrapper);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }


    @Override
    public ResponseResult getTaskResult(Integer id) {
        //参数非空校验
        if (Objects.isNull(id) || id <= 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        //根据taskId查询数据库
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getTaskId, id);

        //查询该用户是否是管理员，若否则加入过滤条件
        if(!userService.isAdmin()){
            wrapper.eq(Task::getUserId, SecurityUtils.getUserId());
        }
        Task task = getOne(wrapper);
        if(Objects.isNull(task)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "用户任务列表里没有该数据！");
        } else if(!TaskStatusEnum.COMPLETED.getStatusCode().equals(task.getStatus())){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "任务未执行完成");
        } else if(StringUtils.hasText(task.getResult())){
            Object o = JSON.parseObject(task.getResult(), Object.class);
            return ResponseResult.okResult(o);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
