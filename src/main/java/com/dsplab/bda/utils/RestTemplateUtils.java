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
     * @param body 请求体
     * @return json格式响应体
     */
    public JSONObject doPostForObject(String url, String body) {
        return restTemplate.postForObject(url, body, JSONObject.class);
    }

    /**
     * 发送post请求
     * @param url 请求url
     * @param body 请求体
     * @return 响应全部信息
     */
    public ResponseEntity<Object> doPostForEntity(String url, String body) {
        return  restTemplate.postForEntity(url, body, Object.class);
    }

    /**
     * 发送get请求
     * @param url 请求url
     * @param urlValues 请求字段,map集合
     *                  也可在url上添加参数
     * @return 响应全部信息
     */
    public ResponseEntity<Object> doGetForEntity(String url, Map<String, ?> urlValues) {
        if(urlValues == null){
            return restTemplate.getForEntity(url, Object.class);
        }
        return restTemplate.getForEntity(url, Object.class, urlValues);
    }

    /**
     * 发送get请求
     * @param url 请求url
     * @param urlValues 请求字段,map集合
     * @return json格式响应体
     */
    public JSONObject doGetForObject(String url, Map<String, ?> urlValues){
        if(urlValues == null){
            return restTemplate.getForObject(url, JSONObject.class);
        }
        return restTemplate.getForObject(url, JSONObject.class, urlValues);
    }
}
