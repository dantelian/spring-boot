package com.example.springbootdesignpattern.common.structuralMode.decoratorPattern;

/**
 * @program: spring-boot-design-pattern
 * @description: 长方形
 * @author: ddd
 * @create: 2023-02-07 15:13
 **/
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("长方形");
    }
}
