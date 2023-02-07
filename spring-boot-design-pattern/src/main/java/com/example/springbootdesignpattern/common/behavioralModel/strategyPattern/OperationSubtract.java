package com.example.springbootdesignpattern.common.behavioralModel.strategyPattern;

/**
 * @program: spring-boot-design-pattern
 * @description:
 * @author: ddd
 * @create: 2023-02-07 16:42
 **/
public class OperationSubtract implements Strategy{
    @Override
    public int doOperation(int num1, int num2) {
        return num1 - num2;
    }
}
