package com.dsplab.bda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dsplab.bda.mapper")
public class BdaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdaApplication.class, args);
    }

}
