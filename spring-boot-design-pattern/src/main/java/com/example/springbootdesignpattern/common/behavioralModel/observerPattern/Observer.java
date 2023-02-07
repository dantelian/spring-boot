package com.example.springbootdesignpattern.common.behavioralModel.observerPattern;

/**
 * @program: spring-boot-design-pattern
 * @description:
 * @author: ddd
 * @create: 2023-02-07 17:06
 **/
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
