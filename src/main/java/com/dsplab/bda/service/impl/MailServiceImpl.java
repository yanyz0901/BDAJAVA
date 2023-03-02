package com.dsplab.bda.service.impl;

import com.dsplab.bda.domain.vo.MailVo;
import com.dsplab.bda.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class MailServiceImpl implements MailService {
    private Logger logger = (Logger) LoggerFactory.getLogger(getClass());

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public void sendMail() {
        String subject = "任务执行结果";
        String text = "执行成功，结果已保存到数据库";
        MailVo mailVo = new MailVo(this.getMailSendTo(),subject,text);
        try{
            checkMail(mailVo);
            createMail(mailVo);
        }catch (Exception e){
            logger.error("发送邮件失败",e);
            mailVo.setStatus("fail");
            mailVo.setError(e.getMessage());
        }
    }

    @Override
    public MailVo sendMail(MailVo mailVo) {
        try{
            checkMail(mailVo);
            createMail(mailVo);
            return saveMail(mailVo);
        }catch (Exception e){
            logger.error("发送邮件失败",e);
            mailVo.setStatus("fail");
            mailVo.setError(e.getMessage());
            return mailVo;
        }
    }

    @Override
    public void checkMail(MailVo mailVo) {
        if(StringUtils.isEmpty(mailVo.getTo())){
            throw new RuntimeException("邮件收信人不能为空");
        }
        if(StringUtils.isEmpty(mailVo.getSubject())){
            throw new RuntimeException("邮件主题不能为空");
        }
        if(StringUtils.isEmpty(mailVo.getText())) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }

    @Override
    public void createMail(MailVo mailVo) {
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailSender.createMimeMessage(),true);
            mailVo.setFrom(getMailSendFrom());
            mimeMessageHelper.setFrom(mailVo.getFrom());
            mimeMessageHelper.setTo(mailVo.getTo().split(","));
            mimeMessageHelper.setSubject(mailVo.getSubject());
            mimeMessageHelper.setText(mailVo.getText());

            if(!StringUtils.isEmpty(mailVo.getCc())){ //抄送
                mimeMessageHelper.setCc(mailVo.getCc().split(","));
            }
            if(!StringUtils.isEmpty(mailVo.getBcc())){ //密送
                mimeMessageHelper.setBcc(mailVo.getBcc().split(","));
            }
            if(mailVo.getMultipartFiles()!=null){
                for(MultipartFile multipartFile : mailVo.getMultipartFiles()){
                    mimeMessageHelper.addAttachment(multipartFile.getOriginalFilename(),multipartFile);
                }
            }
            if(mailVo.getSentDate()==null){
                mailVo.setSentDate(new Date());
                mimeMessageHelper.setSentDate(mailVo.getSentDate());
            }
            mailSender.send(mimeMessageHelper.getMimeMessage());
            mailVo.setStatus("ok");
            logger.info("邮件发送成功:{"+mailVo.getFrom()+"}->{"+mailVo.getTo()+"}");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public MailVo saveMail(MailVo mailVo) {
        /**
         * 可以在此编写保存到数据库的代码
         */
        return mailVo;
    }

    private String getMailSendFrom() {
        return mailSender.getJavaMailProperties().getProperty("from");
    }

    private String getMailSendTo() {
        return mailSender.getJavaMailProperties().getProperty("to");
    }
}
