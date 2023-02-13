package com.dsplab.bda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.dsplab.bda.mapper")
@EnableSwagger2
public class BdaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdaApplication.class, args);
    }

}
