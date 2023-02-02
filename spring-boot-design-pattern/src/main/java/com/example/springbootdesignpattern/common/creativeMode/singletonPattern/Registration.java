package com.example.springbootdesignpattern.common.creativeMode.singletonPattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: spring-boot-design-pattern
 * @description: 单例模式-登记式/静态内部类
 * @author: ddd
 * @create: 2023-02-02 15:30
 **/
@Slf4j
public class Registration {
    private static class SingletonHolder {
        private static final Registration INSTANCE = new Registration();
    }
    private Registration (){}
    public static final Registration getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private static int i = 0;

    public static int getInt() {
        int random = (int)(Math.random()*10+1);
        log.info("random:{}", random);
        try {
            Thread.sleep(random*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i++;
    }
}
