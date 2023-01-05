package com.dsplab.bda.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/ping")
    public String helloWorld(){
        return "pong!";
    }
}
