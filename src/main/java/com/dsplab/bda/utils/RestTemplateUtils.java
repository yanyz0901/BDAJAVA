package com.dsplab.bda.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestTemplateUtils {

    @Autowired
    public RestTemplate restTemplate;

    /**
     * 发送post请求
     * @param url 请求url
     * @param param 请求体参数
     * @return 响应
     */
    public JSONObject doPost(String url, String param) {
        return restTemplate.postForObject(url, param, JSONObject.class);
    }

    /**
     * 发送get请求
     * @param url 请求url
     * @param map 请求字段
     * @return 响应
     */
    public ResponseEntity<String> doGet(String url, Map<String, String> map) {
        return restTemplate.getForEntity(url, String.class, map);
    }

}
