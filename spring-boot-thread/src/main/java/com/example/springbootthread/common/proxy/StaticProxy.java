package com.example.springbootthread.common.proxy;

/**
 * @Description 静态代理（模拟婚庆公司实现）
 */
public class StaticProxy {
    public static void main(String[] args) {
        Marry marry = new WeddingCompany(new You());
        marry.happyMarry();
        //注意：真实对象和代理对象要实现同一个接口
    }
}
//结婚
interface Marry {
    //定义一个结婚的接口
    void happyMarry();
}
//你（真实角色）
class You implements Marry {
    @Override
    public void happyMarry() {
        System.out.println("张三结婚了！");
    }
}
//婚庆公司（代理角色）
class WeddingCompany implements Marry {

    //引入真实角色
    private Marry target;

    public WeddingCompany(Marry target) {
        this.target = target;
    }

    @Override
    public void happyMarry() {
        //在结婚前后增加业务
        before();
        target.happyMarry();
        after();
    }
    private void before() {
        System.out.println("结婚之前：布置婚礼现场");
    }
    private void after() {
        System.out.println("结婚之后：收尾工作");
    }
}
