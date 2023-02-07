package com.example.springbootdesignpattern.common.structuralMode.bridgePattern;

/**
 * @program: spring-boot-design-pattern
 * @description:
 * @author: ddd
 * @create: 2023-02-07 16:03
 **/
public class Circle1 extends Shape1 {
    private int x, y, radius;

    public Circle1(int x, int y, int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw() {
        drawAPI.drawCircle(radius,x,y);
    }
}
