package com.example.springbootschedule.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer
 * 这是java自带的java.util.Timer类，这个类允许你调度一个java.util.TimerTask任务。使用这种方式可以让你的程序按照某一个频度执行，但不能在指定时间运行。一般用的较少。
 */
public class TimerTest {
    public static void main(String[] args) {
        // 循环执行
//        circulate();

        // 只执行一次
        singleTime();
    }

    /**
     * 循环执行
     */
    private static void circulate() {
        System.out.println("run:"+ new Date());
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task  run:"+ new Date());
            }
        };
        Timer timer = new Timer();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。这里是在执行方法1秒后每3秒执行一次
        timer.schedule(timerTask,1000,3000);
    }

    /**
     * 只执行一次
     */
    private static void singleTime() {
        Timer timer = new Timer();
        System.out.println("run:"+ new Date());
        // 延迟1000ms执行
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("task1  run:"+ new Date());
            }
        }, 1000);

        // 延迟3000ms执行
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("task2  run:"+ new Date());
            }
        }, new Date(System.currentTimeMillis() + 3000));


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 在需要的时候关闭计时器
        timer.cancel();
    }

}
