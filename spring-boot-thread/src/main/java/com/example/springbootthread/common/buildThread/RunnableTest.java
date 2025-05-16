package com.example.springbootthread.common.buildThread;

public class RunnableTest implements Runnable {

    //票数
    private int ticketNums = 10;

    @Override
    public void run() {
        while (ticketNums >= 0){
            try {
                //让线程睡眠一会
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"-->抢到了第"+ticketNums--+"张票");
        }
    }

    public static void main(String[] args) {
        //创建runnable接口的实现类对象
        RunnableTest demo = new RunnableTest();
        //创建线程对象，通过线程对象开启线程（使用的代理模式）
        //Thread thread = new Thread(demo,"老王");
        //thread.start();
        //简写：new Thread(demo).start();
        new Thread(demo,"老王").start();
        new Thread(demo,"小张").start();
        new Thread(demo,"黄牛党").start();

        //发现问题：多个线程操作同一个资源时，线程不安全，数据紊乱。(线程并发)
    }
}
