package com.example.springbootdesignpattern.common.creativeMode.factoryPattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @program: spring-boot-design-pattern
 * @description: 实现接口的实体类
 * @author: ddd
 * @create: 2023-02-02 16:02
 **/
@Slf4j
public class Cuckoo implements Animal {

    @Override
    public void call() {
        log.info("cuckoo call!");
    }
}
