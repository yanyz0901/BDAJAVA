package com.dsplab.bda.controller;

import com.dsplab.bda.domain.vo.MailVo;
import com.dsplab.bda.service.impl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailServiceImpl mailService;


    @PostMapping("/send")
    public MailVo sendMail(MailVo mailVo){
        return mailService.sendMail(mailVo);
    }
}
