package com.example.springbootthread.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description 动态代理
 */
public class DynamicProxy {
    public static void main(String[] args) {
        //创建目标对象
        Marry1 target = new You1();

        //创建InvocationHandler对象
        MyInvocationHandler handler = new MyInvocationHandler(target);

        //创建代理对象
        Marry1 proxy = (Marry1) handler.getProxy();
        //通过代理执行方法，会调用handle中的invoke()方法
        proxy.happyMarry1();
    }
}
//创建结婚接口
interface Marry1 {
    void happyMarry1();
}
//目标类实现结婚接口
class You1 implements Marry1{
    @Override
    public void happyMarry1() {
        System.out.println("张三结婚了！");
    }
}
//创建工具类，即方法增强的功能
class ServiceTools{
    public void before(){
        System.out.println("结婚之前：布置婚礼现场");
    }
    public void after(){
        System.out.println("结婚之后：清理结婚现场");
    }
}
//创建InvocationHandler的实现类
class MyInvocationHandler implements InvocationHandler {

    //目标对象
    private Object target;

    public MyInvocationHandler(Object target){
        this.target = target;
    }

    //通过代理对象执行方法时，会调用invoke()方法
    /**
     * @Param [proxy：jdk创建的代理类的实例]
     * @Param [method：目标类中被代理方法]
     * @Param [args：目标类中方法的参数]
     * @return java.lang.Object
     **/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //增强功能
        ServiceTools tools = new ServiceTools();
        tools.before();
        //执行目标类中的方法
        Object obj = null;
        obj = method.invoke(target, args);
        tools.after();
        return obj;
    }

    //通过Proxy类创建代理对象（自己手写的嗷）
    /**
     * @Param [ClassLoader loader：类加载器，负责向内存中加载对象的，使用反射获取对象的ClassLoader]
     * @Param [Class<?>[] interfaces： 接口， 目标对象实现的接口，也是反射获取的。]
     * @Param [InvocationHandler h： 我们自己写的，代理类要完成的功能。]
     * @return java.lang.Object
     **/
    public Object getProxy(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
    }
}
