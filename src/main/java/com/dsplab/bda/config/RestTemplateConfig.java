package com.dsplab.bda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateConfig {
    @Value("${resttemplate.parameter.connectTimeout:6000}")
    private Integer connectTimeout;

    //读取超时时间,当配置文件没设定时间时，默认6000ms
    @org.springframework.beans.factory.annotation.Value("${resttemplate.parameter.readTimeout:6000}")
    private Integer readTimeout;

    @Bean
    //@LoadBalanced //客户端对服务器的负载均衡
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);
        factory.setConnectTimeout(connectTimeout);
        return factory;
    }

}
