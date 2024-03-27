package com.example.springbootthread.common.buildThread;

public class ThreadTest extends Thread {

    @Override
    public void run() {
        //新线程入口点
        for (int i = 0; i < 100; i++) {
            System.out.println("多线程："+i);
        }
    }
    //主线程
    public static void main(String[] args) {
        //创建线程对象
        ThreadTest demo = new ThreadTest();
        demo.start();//启动线程
        for (int i = 0; i < 1000; i++) {
            System.out.println("主线程："+i);
        }
        //主线程和多线程并行交替执行
        //总结：线程开启不一定立即执行，由cpu调度执行
    }

}
