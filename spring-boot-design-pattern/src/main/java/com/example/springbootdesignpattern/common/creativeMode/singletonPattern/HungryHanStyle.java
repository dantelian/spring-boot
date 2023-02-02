package com.example.springbootdesignpattern.common.creativeMode.singletonPattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: spring-boot-design-pattern
 * @description: 单例模式-饿汉式
 * @author: ddd
 * @create: 2023-02-02 15:41
 **/
@Slf4j
public class HungryHanStyle {
    private static HungryHanStyle instance = new HungryHanStyle();
    private HungryHanStyle (){}
    public static HungryHanStyle getInstance() {
        return instance;
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
