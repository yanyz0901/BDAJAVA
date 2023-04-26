package com.dsplab.bda.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dsplab.bda.constants.SystemConstants;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.service.ExternalDatabaseService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class ExternalDatabaseServiceImpl implements ExternalDatabaseService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseResult getKEGGList() {
        String response = restTemplate.getForObject(SystemConstants.KEGG_LIST_URL, String.class);
        return ResponseResult.okResult(response);
    }

    @Override
    public ResponseResult getBiGGList() {
        String response = restTemplate.getForObject(SystemConstants.BIGG_LIST_URL, String.class);
        return ResponseResult.okResult(response);
    }

    @Override
    public ResponseResult getKEGGInfoById(String keggId) {
        String response = restTemplate.getForObject(SystemConstants.KEGG_INFO_URL + keggId, String.class);
        return ResponseResult.okResult(response);
    }

    @Override
    public ResponseResult getBiGGInfoById(String biggModelId) {
        String response = restTemplate.getForObject(SystemConstants.BIGG_INFO_URL + biggModelId, String.class);
        return ResponseResult.okResult(response);
    }

    @Override
    public ResponseResult getBioCycInfoById(String biocycId) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            URI uri = new URIBuilder(SystemConstants.BIOCYC_INFO_URL).setParameter("id", biocycId).setParameter("format", "json").build();
            HttpGet httpGet = new HttpGet(uri);
            response = httpclient.execute(httpGet);
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(content);
            return ResponseResult.okResult(jsonObject);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        } finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
