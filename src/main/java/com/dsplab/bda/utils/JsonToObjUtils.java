package com.dsplab.bda.utils;

import com.alibaba.fastjson.JSONObject;
import com.dsplab.bda.domain.vo.StartTaskVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import springfox.documentation.spring.web.json.Json;
import com.alibaba.fastjson.JSON;

import java.util.Objects;

/***
 * json格式转成object工具
 */
public class JsonToObjUtils {
    public static StartTaskVo jsonConvert(String jsonsubject){
        StartTaskVo ConvertedObject = JSON.parseObject(jsonsubject,StartTaskVo.class);
        return ConvertedObject;
    }

    public static String objectTojson(StartTaskVo objects)  {
        String json =JSON.toJSONString(objects);
        return json;

    }
}
