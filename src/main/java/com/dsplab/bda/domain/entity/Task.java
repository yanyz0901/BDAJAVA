package com.dsplab.bda.domain.entity;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Task)表实体类
 *
 * @author makejava
 * @since 2023-01-06 11:28:55
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("task")
public class Task  {
    //任务id
    @TableId
    private Long taskId;
    //创建人id
    private Long userId;
    //任务创建时间
    private Date createTime;
    //任务开始时间
    private Date startTime;
    //任务完成时间
    private Date completeTime;
    //任务状态(0未开始 1进行中 2已完成)
    private String status;
    //任务配置详情
    private String configInfo;
    //任务结果
    private String result;
    //删除标志（0代表未删除，1代表已删除）
    @TableLogic
    private String delFlag;
}

