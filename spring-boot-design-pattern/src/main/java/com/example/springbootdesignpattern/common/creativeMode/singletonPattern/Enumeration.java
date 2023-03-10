package com.example.springbootdesignpattern.common.creativeMode.singletonPattern;

import com.example.springbootdesignpattern.common.holder.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: spring-boot-design-pattern
 * @description: 单例模式-枚举
 * @author: ddd
 * @create: 2023-02-02 15:30
 **/
@Slf4j
public enum Enumeration {
    INSTANCE;

//    private static XXService xxService = SpringContextHolder.getBean(XXService.class);
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
