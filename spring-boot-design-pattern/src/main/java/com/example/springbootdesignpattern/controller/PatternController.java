package com.example.springbootdesignpattern.controller;

import com.example.springbootdesignpattern.common.creativeMode.factoryPattern.Animal;
import com.example.springbootdesignpattern.common.creativeMode.factoryPattern.AnimalFactory;
import com.example.springbootdesignpattern.common.creativeMode.singletonPattern.DCL;
import com.example.springbootdesignpattern.common.creativeMode.singletonPattern.Enumeration;
import com.example.springbootdesignpattern.common.creativeMode.singletonPattern.HungryHanStyle;
import com.example.springbootdesignpattern.common.creativeMode.singletonPattern.Registration;
import com.example.springbootdesignpattern.common.structuralMode.decoratorPattern.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot-design-pattern
 * @description:
 * @author: ddd
 * @create: 2023-02-02 11:07
 **/
@Slf4j
@RestController
@RequestMapping("/pattern")
public class PatternController {

    // 单例模式
    @GetMapping("/singletonPattern")
    public String testSingletonPattern() {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    log.info("int:{}", Enumeration.getInt());
//                    log.info("int:{}", Registration.getInt());
//                    log.info("int:{}", DCL.getInt());
                    log.info("int:{}", HungryHanStyle.getInt());
                }
            }).start();

        }
        long t2 = System.currentTimeMillis();
        log.info("time:{}", t2 - t1);

        return "success!";
    }

    // 工厂模式
    @GetMapping("/factoryPattern")
    public String factoryPattern() {
        AnimalFactory factory = new AnimalFactory();

        Animal animal1 = factory.getAnimal("cuckoo");
        animal1.call();

        Animal animal2 = factory.getAnimal("dog");
        animal2.call();

        return "success!";
    }

    // 装饰器模式
    @GetMapping("/decoratorPattern")
    public String decoratorPattern() {
        Shape circle = new Circle();
        ShapeDecorator redCircle = new RedShapeDecorator(new Circle());
        ShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());
        //Shape redCircle = new RedShapeDecorator(new Circle());
        //Shape redRectangle = new RedShapeDecorator(new Rectangle());
        System.out.println("Circle with normal border");
        circle.draw();

        System.out.println("\nCircle of red border");
        redCircle.draw();

        System.out.println("\nRectangle of red border");
        redRectangle.draw();

        return "success!";
    }

}
