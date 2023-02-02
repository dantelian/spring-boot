package com.example.springbootdesignpattern.common.creativeMode.factoryPattern;

/**
 * @program: spring-boot-design-pattern
 * @description: 创建一个工厂，生成基于给定信息的实体类的对象。
 * @author: ddd
 * @create: 2023-02-02 16:04
 **/
public class AnimalFactory {

    //使用 getShape 方法获取形状类型的对象
    public Animal getAnimal(String animal){
        if(animal == null){
            return null;
        }

        switch (animal) {
            case "cuckoo":
                return new Cuckoo();
            case "dog":
                return new Dog();
        }
        return null;
    }
}
