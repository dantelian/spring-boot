package com.example.springbootdesignpattern.controller;

import com.example.springbootdesignpattern.common.creativeMode.factoryPattern.Animal;
import com.example.springbootdesignpattern.common.creativeMode.factoryPattern.AnimalFactory;
import com.example.springbootdesignpattern.common.creativeMode.singletonPattern.DCL;
import com.example.springbootdesignpattern.common.creativeMode.singletonPattern.Enumeration;
import com.example.springbootdesignpattern.common.creativeMode.singletonPattern.HungryHanStyle;
import com.example.springbootdesignpattern.common.creativeMode.singletonPattern.Registration;
import com.example.springbootdesignpattern.common.structuralMode.adapterPattern.AudioPlayer;
import com.example.springbootdesignpattern.common.structuralMode.bridgePattern.Circle1;
import com.example.springbootdesignpattern.common.structuralMode.bridgePattern.GreenCircle;
import com.example.springbootdesignpattern.common.structuralMode.bridgePattern.RedCircle;
import com.example.springbootdesignpattern.common.structuralMode.bridgePattern.Shape1;
import com.example.springbootdesignpattern.common.structuralMode.decoratorPattern.*;
import com.example.springbootdesignpattern.common.structuralMode.proxyPattern.Image;
import com.example.springbootdesignpattern.common.structuralMode.proxyPattern.ProxyImage;
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

    // 代理模式
    @GetMapping("/proxyPattern")
    public String proxyPattern() {
        Image image = new ProxyImage("proxyPattern.jpg");

        // 图像将从磁盘加载
        image.display();
        System.out.println("");
        // 图像不需要从磁盘加载
        image.display();

        return "success!";
    }

    // 桥接模式
    @GetMapping("/bridgePattern")
    public String bridgePattern() {
        Shape1 redCircle = new Circle1(100,100, 10, new RedCircle());
        Shape1 greenCircle = new Circle1(100,100, 10, new GreenCircle());

        redCircle.draw();
        greenCircle.draw();

        return "success!";
    }

    // 适配器模式
    @GetMapping("/adapterPattern")
    public String adapterPattern() {
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "beyond the horizon.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far far away.vlc");
        audioPlayer.play("avi", "mind me.avi");

        return "success!";
    }

}
