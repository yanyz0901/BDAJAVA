package com.dsplab.bda.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Hostcell)实体类
 *
 * @author makejava
 * @since 2023-12-03 14:46:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("hostcell")
public class Hostcell {
    @TableId
    private Long hostcellId;
    
    private Long taskId;
    
    private String ftpPath;
    
    private String fileName;
    
    private Long userId;
    
    private String hostcellName;
}

