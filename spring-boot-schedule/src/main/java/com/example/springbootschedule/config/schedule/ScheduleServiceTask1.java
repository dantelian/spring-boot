package com.example.springbootschedule.config.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduleServiceTask1 {
    /**
     * 任务执行的cron表达式
     * @Scheduled(cron = "{秒数} {分钟} {小时} {日期} {月份} {星期}")
     *
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
        log.info("=====>>>>>使用cron1 {}",System.currentTimeMillis());
    }
}
