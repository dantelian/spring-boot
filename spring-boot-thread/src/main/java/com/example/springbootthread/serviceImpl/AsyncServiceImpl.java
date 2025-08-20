package com.example.springbootthread.serviceImpl;

import com.example.springbootthread.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger log = LoggerFactory.getLogger(AsyncServiceImpl.class);

    // 指定线程池
    @Async("taskExecutor")
    @Override
    public void notResult(String to) {
        log.info("异步发送邮件给: {}", to);
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        log.info("邮件发送完成");
    }

    @Async
    @Override
    public Future<String> futureResult() {
        try { Thread.sleep(5000); } catch (InterruptedException e) {}
        log.info("do Task");
        return new AsyncResult<>("Task completed");
    }

    @Async
    @Override
    public CompletableFuture<String> completableFutureResult() {
        try { Thread.sleep(5000); } catch (InterruptedException e) {}
        return CompletableFuture.completedFuture("Async Result");
    }

    @Async
    @Override
    public CompletableFuture<String> completableFutureResult1() {
        long time = (long) Math.floor(Math.random() * 10) * 1000;
        log.info("time: {}", time);
        try { Thread.sleep(time); } catch (InterruptedException e) {}
        return CompletableFuture.completedFuture("Async Result1");
    }

    @Override
    public void completableFutureResult2() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            long time = (long) Math.floor(Math.random() * 10) * 1000;
            log.info("time: {}", time);
            try { Thread.sleep(time); } catch (InterruptedException e) {}
            System.out.println("Async run");
        });
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }


}
