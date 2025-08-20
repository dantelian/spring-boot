package com.example.springbootthread.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface AsyncService {

    /**
     * 无返回值
     */
    void notResult(String to);

    /**
     * Future<T> 返回结果
     * @return
     */
    Future<String> futureResult();

    /**
     * CompletableFuture<T> 返回结果
     * @return
     */
    CompletableFuture<String> completableFutureResult();

    CompletableFuture<String> completableFutureResult1();

    void completableFutureResult2();

}
