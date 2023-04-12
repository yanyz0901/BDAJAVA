package com.dsplab.bda.cornjob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
@Slf4j
public class CornJobs {

    @Scheduled(cron = "0/30 * * * * ?")
    public void testJob(){
        Date date = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(timeZone);
        log.info("BDA定时任务启动，当前时间为：" + simpleDateFormat.format(date));
    }
}
