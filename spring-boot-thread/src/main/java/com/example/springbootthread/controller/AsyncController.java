package com.example.springbootthread.controller;

import com.example.springbootthread.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    public AsyncService asyncService;

    @GetMapping("/notResult")
    public String notResult() {
        for (int i = 0; i < 10; i++) {
            asyncService.notResult(i + "");
        }
        return "ok";
    }

    @GetMapping("/futureResult")
    public String futureResult() throws ExecutionException, InterruptedException {
        Future<String> future = asyncService.futureResult();
        String result = future.get(); // 阻塞等待结果
        log.info("result: {}", result);
        return "ok";
    }

    @GetMapping("/completableFutureResult")
    public String completableFutureResult() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = asyncService.completableFutureResult();
        // 非阻塞，可以做其他事，在future结束时自动调用该回调函数，这样我们就不用等待结果
        String result = future.get(); // 阻塞获取
        log.info("result: {}", result);
        return "ok";
    }

    /**
     * 并行调用多个服务
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/thenCombine")
    public String thenCombine() throws ExecutionException, InterruptedException {
        log.info("thenCombine");
        CompletableFuture<Object> result = asyncService.completableFutureResult()
                .thenCombine(asyncService.completableFutureResult1(),
                        (result1, result2) -> result1 + result2);
        log.info("result: {}", result.get());
        return "ok";
    }

    @GetMapping("/batch")
    public String batch() throws ExecutionException, InterruptedException {
        log.info("batch");
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            futures.add(asyncService.completableFutureResult1());
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
//        allOf.get(); // 会阻塞主线程直到任务完成，并返回 null（因为 CompletableFuture<Void> 不返回结果）。
        allOf.join(); // 不会阻塞主线程，但会等待任务完成
        for (int i = 0; i < 10; i++) {
            log.info("result: {}", futures.get(i).get());
        }
        return "ok";
    }

    @GetMapping("/completableFutureResult2")
    public String completableFutureResult2() throws ExecutionException, InterruptedException {
        log.info("completableFutureResult2");
        for (int i = 0; i < 10; i++) {
            asyncService.completableFutureResult2();
        }
        log.info("completableFutureResult2 end");
        return "ok";
    }

}
