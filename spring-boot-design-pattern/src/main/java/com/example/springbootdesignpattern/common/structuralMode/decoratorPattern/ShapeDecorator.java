package com.example.springbootdesignpattern.common.structuralMode.decoratorPattern;

/**
 * @program: spring-boot-design-pattern
 * @description: 形状抽象装饰类
 * @author: ddd
 * @create: 2023-02-07 15:15
 **/
public abstract class ShapeDecorator implements Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape){
        this.decoratedShape = decoratedShape;
    }

    public void draw(){
        decoratedShape.draw();
    }
}
