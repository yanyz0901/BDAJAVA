package com.dsplab.bda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Task;
import com.dsplab.bda.domain.entity.User;
import com.dsplab.bda.domain.vo.PageVo;
import com.dsplab.bda.domain.vo.StartTaskVo;
import com.dsplab.bda.domain.vo.TaskVo;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.mapper.TaskMapper;
import com.dsplab.bda.mapper.UserMapper;
import com.dsplab.bda.service.TaskService;
import com.dsplab.bda.service.UserService;
import com.dsplab.bda.utils.*;
import io.lettuce.core.output.ScanOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
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
    private TaskMapper taskMapper;


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

        if (Objects.nonNull(task)) return ResponseResult.okResult(taskVo);
        else {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "未查询到结果");
        }
    }

    @Override
    public ResponseResult taskList(Integer pageNum, Integer pageSize) {
        //参数非空校验
        if (Objects.isNull(pageNum) || Objects.isNull(pageSize)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }

        //查询该用户是否是管理员，若否则加入过滤条件
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        if(!userService.isAdmin()){
            wrapper.eq(Task::getUserId, SecurityUtils.getUserId());
        }

        //分页查询
        Page<Task> page = new Page<>(pageNum, pageSize);
        page(page,wrapper);
        List<Task> taskList = page.getRecords();

        //封装vo
        List<TaskVo> taskVos = BeanCopyUtils.copyBeanList(taskList, TaskVo.class);
        for (TaskVo taskVo : taskVos) {
            taskVo.setUserName(userService.getById(SecurityUtils.getUserId()).getUserName());
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
    public ResponseResult startTask(Integer id) throws Exception{
        //参数非空校验
        if(Objects.isNull(id) || id <= 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.INPUT_NOT_NULL);
        }
        //根据输入的任务id找到对应任务
        LambdaQueryWrapper<Task> wrappertask = new LambdaQueryWrapper<>();
        wrappertask.eq(Task::getTaskId, id);
        Task task = taskMapper.selectOne(wrappertask);
        //判断的任务中user_id，与当前的用户的id是否一致，不一致则报错
        if(task.getUserId()!=SecurityUtils.getUserId()){
            log.info("the user_id in the task are not match the now user");
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"当前用户id与该任务中的用户id不符");
        }
        //根据输入的任务id来实现任务
        String task_id = Long.toString(id);
        //根据id从数据库中获取任务信息
        String Taskinfo = infoGetUtils.get("http://localhost:8888/task/startTask"+"{"+task_id+"}");
        //将任务信息从json转为StartTaskVo格式，然后在里面加入task_id，再重新转为json格式
        StartTaskVo taskVo = JsonToObjUtils.jsonConvert(Taskinfo);
        taskVo.setTask_id(id.intValue());
        String Taskinfo2 = JsonToObjUtils.objectTojson(taskVo);
        //发送给算法api
        String result = infoPostUtils.sendPost("http://localhost:9999/task/moooseeker/",Taskinfo2);
        System.out.println("result=="+result);//返回算法的响应

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
