# spring-boot-schedule

## Timer

> 这是java自带的java.util.Timer类，这个类允许你调度一个java.util.TimerTask任务。使用这种方式可以让你的程序按照某一个频度执行，但不能在指定时间运行。一般用的较少。  
> [TimerTest.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fspringbootschedule%2Ftimer%2FTimerTest.java)

## ScheduledExecutorService

> jdk自带的一个类；是基于线程池设计的定时任务类,每个调度任务都会分配到线程池中的一个线程去执行,也就是说,任务是并发执行,互不影响。  
> [ScheduledExecutorServiceTest.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fspringbootschedule%2FscheduledExecutorService%2FScheduledExecutorServiceTest.java)

## Spring Task

> Spring3.0以后自带的task，可以将它看成一个轻量级的Quartz。  
> 启动类配合@EnableScheduling使用。  
> 多个任务会使同一个线程串行执行，可能会造成堵塞，可以使用异步执行。@EnableAsync @Async  
> [ScheduleServiceTask.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fspringbootschedule%2Fconfig%2Fschedule%2FScheduleServiceTask.java)


## Quartz

> 这是一个功能比较强大的的调度器，可以让你的程序在指定时间执行，也可以按照某一个频度执行，配置起来稍显复杂。  
> [QuartzConfig.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fspringbootschedule%2Fquartz%2Fconfig%2FQuartzConfig.java)  
> [TestQuartz.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fspringbootschedule%2Fquartz%2Fjob%2FTestQuartz.java)

````xml
<!--quartz依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-quartz</artifactId>
</dependency>
````



> 参考：<https://www.jb51.net/program/304716axu.htm>




