package com.dsplab.bda.service;

import com.dsplab.bda.domain.vo.MailVo;

public interface MailService {
    public void sendMail(String sendToAddress);
    public void sendMail(MailVo mailVo);
    public void checkMail(MailVo mailVo);
    public void createMail(MailVo mailVo);
    public MailVo saveMail(MailVo mailVo);
}
