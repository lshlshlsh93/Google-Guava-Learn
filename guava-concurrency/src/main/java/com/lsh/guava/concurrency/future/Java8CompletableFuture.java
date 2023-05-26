package com.lsh.guava.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author lishaohui
 * @Date 2023/5/26 16:29
 */
public class Java8CompletableFuture {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        }, executorService).whenComplete(
                (v, t) -> System.out.println("Finished task. The result is : " + v)
        );

    }

}
