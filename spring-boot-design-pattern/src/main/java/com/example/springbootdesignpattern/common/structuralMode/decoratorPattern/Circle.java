package com.example.springbootdesignpattern.common.structuralMode.decoratorPattern;

/**
 * @program: spring-boot-design-pattern
 * @description: 圆形
 * @author: ddd
 * @create: 2023-02-07 15:14
 **/
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("圆形");
    }
}
