package com.dsplab.bda.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.dsplab.bda.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        String[] setterNames = metaObject.getSetterNames();
        HashSet<String> setterNameSet = new HashSet<>(Arrays.asList(setterNames));

        if(setterNameSet.contains("createTime")){
            this.setFieldValByName("createTime", new Date(), metaObject);
        }
        if(setterNameSet.contains("updateTime")){
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }
        if(setterNameSet.contains("status")){
            this.setFieldValByName("status", "0", metaObject);
        }
        if(setterNameSet.contains("type")){
            this.setFieldValByName("type", "0", metaObject);
        }
        if(setterNameSet.contains("userId")){
            this.setFieldValByName("userId", SecurityUtils.getUserId(), metaObject);
        }
        if(setterNameSet.contains("result")){
            this.setFieldValByName("result", "", metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String[] setterNames = metaObject.getSetterNames();
        HashSet<String> setterNameSet = new HashSet<>(Arrays.asList(setterNames));

        if(setterNameSet.contains("updateTime")){
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }

    }
}