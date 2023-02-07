package com.example.springbootdesignpattern.common.behavioralModel.strategyPattern;

/**
 * @program: spring-boot-design-pattern
 * @description:
 * @author: ddd
 * @create: 2023-02-07 16:43
 **/
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2){
        return strategy.doOperation(num1, num2);
    }
}
