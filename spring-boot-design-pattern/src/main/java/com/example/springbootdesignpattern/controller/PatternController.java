package com.example.springbootdesignpattern.controller;

import com.example.springbootdesignpattern.common.creativeMode.singletonPattern.Enumeration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot-design-pattern
 * @description:
 * @author: ddd
 * @create: 2023-02-02 11:07
 **/
@Slf4j
@RestController
@RequestMapping("/pattern")
public class PatternController {

    @GetMapping("/test")
    public String test() {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    log.info("int:{}", Enumeration.getInt());
                }
            }).start();

        }
        long t2 = System.currentTimeMillis();
        log.info("time:{}", t2 - t1);

        return "success!";
    }

}
