package com.example.springbootschedule.config.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Scheduled
 * Spring3.0以后自带的task，可以将它看成一个轻量级的Quartz。
 * 多个任务会使同一个线程串行执行，可能会造成堵塞，可以使用异步执行。@EnableAsync @Async
 */
@Slf4j
@Component
@EnableAsync
public class ScheduleServiceTask {

    /**
     * 任务执行的cron表达式
     * @Scheduled(cron = "{秒数} {分钟} {小时} {日期} {月份} {星期}")
     * 星期（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
        log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    }

    /**
     * 固定速率
     * 上一次任务执行开始到下一次执行开始的间隔时间固定，单位为ms。
     * 若在调度任务执行时,上一次任务还未执行完毕,会加入worker队列,等待上一次执行完成后，马上执行下一次任务
     */
    @Async
    @Scheduled(fixedRate = 5000)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    }

    /**
     *  固定延迟
     * 上一次任务执行结束到下一次执行开始的间隔时间固定，单位为ms。
     */
//    @Async
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>使用fixedDelay{}",System.currentTimeMillis());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
