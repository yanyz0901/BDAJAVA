package com.dsplab.bda;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.domain.entity.Task;
import com.dsplab.bda.domain.entity.User;
import com.dsplab.bda.domain.vo.StartTaskVo;
import com.dsplab.bda.enums.AppHttpCodeEnum;
import com.dsplab.bda.mapper.TaskMapper;
import com.dsplab.bda.mapper.UserMapper;
import com.dsplab.bda.service.TaskService;
import com.dsplab.bda.service.UserService;
import com.dsplab.bda.service.impl.TaskServiceImpl;
import com.dsplab.bda.utils.JsonToObjUtils;
import com.dsplab.bda.utils.SecurityUtils;
import com.dsplab.bda.utils.infoGetUtils;
import com.dsplab.bda.utils.infoPostUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.runner.notification.RunListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Wrapper;
import java.util.List;
import java.util.Objects;




@SpringBootTest
class BdaApplicationTests {

    @Test
    void contextLoads() {
    }

}
@SpringBootTest
class jsontest{
    private static String HELLO_NAME= "{\"populationsize\":\"100\",\"iterations\":\"100\",\"maximumlength\":\"50\",\"targetcompound\":\"C00001\",\"initialcompound\":\"C00004\",\"dataset\":\"KEGG\",\"hostcell\":\"Yeast\",\"evaluation\":\"路径长度\"}";
    @Test
    void justtest(){
        StartTaskVo hello = JsonToObjUtils.jsonConvert(HELLO_NAME);
        System.out.println(hello);
    }
}

@SpringBootTest
class okTest {

    @org.junit.jupiter.api.Test
    public void test() throws Exception{
        //String result = infoGetUtils.get("http://localhost:8888/task/18");
        //System.out.println("result=="+result);//返回算法的响应
        //TaskServiceImpl ok = new TaskServiceImpl();
        //ok.startTask(18);
        //ok.getTaskInfoById((long)17);
    }

    /**
     * get请求
     * @param url
     * @return
     * @throws Exception
     */


}

