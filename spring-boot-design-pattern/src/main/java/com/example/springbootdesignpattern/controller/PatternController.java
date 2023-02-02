package com.example.springbootdesignpattern.controller;

import com.example.springbootdesignpattern.common.creativeMode.singletonPattern.Enumeration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot-design-pattern
 * @description:
 * @author: ddd
 * @create: 2023-02-02 11:07
 **/
@RestController
@RequestMapping("/pattern")
public class PatternController {

    @GetMapping("/test")
    public String test() {

        System.out.println(Enumeration.getInt());

        return "success!";
    }

}
