package com.example.springbootdesignpattern.common.structuralMode.decoratorPattern;

/**
 * @program: spring-boot-design-pattern
 * @description:
 * @author: ddd
 * @create: 2023-02-07 15:16
 **/
public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setRedBorder(decoratedShape);
    }

    private void setRedBorder(Shape decoratedShape){
        System.out.println("Border Color: Red");
    }

}
