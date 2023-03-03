package com.dsplab.bda.service;

import com.dsplab.bda.domain.vo.MailVo;

public interface MailService {
    public void sendMail(String sendToAddress);
    public MailVo sendMail(MailVo mailVo);
    public void checkMail(MailVo mailVo);
    public void createMail(MailVo mailVo);
    public MailVo saveMail(MailVo mailVo);
}
