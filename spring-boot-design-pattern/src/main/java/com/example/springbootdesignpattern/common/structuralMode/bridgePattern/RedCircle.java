package com.example.springbootdesignpattern.common.structuralMode.bridgePattern;

/**
 * @program: spring-boot-design-pattern
 * @description:
 * @author: ddd
 * @create: 2023-02-07 16:02
 **/
public class RedCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: red, radius: " + radius +", x: " +x+", "+ y +"]");
    }
}
