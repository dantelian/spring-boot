# spring-boot-design-pattern

设计模式，三大分类：创建型模式、结构型模式、行为型模式

## 创建型模式（creativeMode）

对象实例化的模式，创建型模式用于解耦对象的实例化过程。

- 单例模式：某个类智能有一个实例，提供一个全局的访问点。  
    > 一般情况下，不建议使用“懒汉方式”。  
    建议使用“饿汉方式”。  
    只有在要明确实现 lazy loading 效果时，才会使用“登记方式”。  
    如果涉及到反序列化创建对象时，可以尝试使用“枚举方式”。  
    如果有其他特殊的需求，可以考虑使用“双检锁方式”。  

    ___测试方法：/pattern/singletonPattern___

| 方式 | 是否 Lazy 初始化 | 是否多线程安全 | 示例 |
| :----: | :----: | :----: | :----: |
| 枚举 | 否 | 是 | Enumeration |
| 登记式/静态内部类 | 是 | 是 | Registration |
| 双检锁/双重校验锁 | 是 | 是 | DCL |
| 饿汉式 | 否 | 是 | HungryHanStyle |

- 工厂模式：一个工厂类根据传入的参量决定创建出哪一种产品类的实例。
    
    1. 创建接口(Animal)
    2. 实现接口的实体类(Cuckoo、Dog)
    3. 创建一个工厂，生成基于给定信息的实体类的对象(AnimalFactory)。

    ___测试方法：/pattern/factoryPattern___

- 抽象工厂模式：创建相关或依赖对象的家族，而无需明确指定具体类。
    
    略.
    
- 建造者模式：封装一个复杂对象的创建过程，并可以按步骤构造。
    
    略.
    
- 原型模式：通过复制现有的实例来创建新的实例。
    
    略.

## 结构型模式（structuralMode）

把类或对象结合在一起形成一个更大的结构。

- 装饰器模式：动态的给对象添加新的功能。
    
    1. 创建接口类（Shape）
    2. 创建方法类（Rectangle、Circle）
    3. 创建抽象装饰器类（ShapeDecorator）
    4. 创建装饰器类（RedShapeDecorator）
    
    ___测试方法：/pattern/decoratorPattern___
    
- 代理模式：为其它对象提供一个代理以便控制这个对象的访问。

    1. 创建接口类（Image）
    2. 创建实现接口的实体类（RealImage）
    3. 创建代理类（ProxyImage）
    
    ___测试方法：/pattern/proxyPattern___
    
- 桥接模式：将抽象部分和它的实现部分分离，使它们都可以独立的变化。

    1. 创建桥接实现接口类（DrawAPI）
    2. 创建实现了 DrawAPI 接口的实体桥接实现类（GreenCircle、RedCircle）
    3. 使用 DrawAPI 接口创建抽象类 Shape（Shape1）
    4. 创建实现了 Shape 抽象类的实体类（Circle1）

    ___测试方法：/pattern/bridgePattern___

- 适配器模式：将一个类的方法接口转换成客户希望的另一个接口。
    
    1. 两套不同的接口（MediaPlayer、AdvancedMediaPlayer）
    2. 实现了 AdvancedMediaPlayer 接口的实体类（VlcPlayer、Mp4Player）
    3. 实现了 MediaPlayer 接口的适配器类（MediaAdapter）
    4. 实现了 MediaPlayer 接口的实体类（AudioPlayer）
    
    ___测试方法：/pattern/adapterPattern___

- 组合模式：将对象组合成树形结构以表示“部分-整体”的层次结构。
    
    略.
    
- 外观模式：对外提供一个统一的方法，来访问子系统中的一群接口。
    
    略.
    
- 享元模式：通过共享技术来有效的支持大量细粒度的对象。
    
    略.


## 行为型模式（behavioralModel）

类和对象如何交互，及划分责任和算法。

- 策略模式：定义一系列算法，把他们封装起来，并且使它们可以相互替换。

    1. 策略接口（Strategy）
    2. 实现策略类（OperationAdd、OperationSubtract、OperationMultiply）
    3. 创建 Context 类（Context）
    
    ___测试方法：/pattern/strategyPattern___

- 模板模式：定义一个算法结构，而将一些步骤延迟到子类实现。
    
    略.

- 命令模式：将命令请求封装为一个对象，使得可以用不同的请求来进行参数化。
    
    略.

- 迭代器模式：一种遍历访问聚合对象中各个元素的方法，不暴露该对象的内部结构。
    
    略.

- 观察者模式：对象间的一对多的依赖关系。

    1. 创建 Subject 类(Subject)
    2. 创建 Observer 类(Observer)
    3. 创建实体观察者类(HexaObserver、OctalObserver、BinaryObserver)
    
    ___测试方法：/pattern/observerPattern___

- 仲裁者模式：用一个中介对象来封装一系列的对象交互。
    
    略.
    
- 备忘录模式：在不破坏封装的前提下，保持对象的内部状态。
    
    略.

- 解释器模式：给定一个语言，定义它的文法的一种表示，并定义一个解释器。
    
    略.

- 状态模式：允许一个对象在其对象内部状态改变时改变它的行为。
    
    略.

- 责任链模式：将请求的发送者和接收者解耦，使的多个对象都有处理这个请求的机会。

    1. 创建抽象的记录器类（创建抽象的记录器类）
    2. 创建扩展了该记录器类的实体类（ConsoleLogger、FileLogger、ErrorLogger）
    
    ___测试方法：/pattern/chainOfResponsibilityPattern___

- 访问者模式：不改变数据结构的前提下，增加作用于一组对象元素的新功能。
    
    略.

参考：  
<https://blog.csdn.net/guorui_java/article/details/104026988>  
[菜鸟]<https://www.runoob.com/design-pattern/design-pattern-tutorial.html>  

