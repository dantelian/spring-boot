package com.example.springbootdesignpattern.common.creativeMode.singletonPattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: spring-boot-design-pattern
 * @description: 单例模式-双检锁/双重校验锁（DCL，即 double-checked locking）
 * @author: ddd
 * @create: 2023-02-02 15:34
 **/
@Slf4j
public class DCL {

    private volatile static DCL instance;
    private DCL (){}
    public static DCL getInstance() {
        if (instance == null) {
            synchronized (DCL.class) {
                if (instance == null) {
                    instance = new DCL();
                }
            }
        }
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
