package com.lsh.guava.concurrency.ratelimiter.guava_ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/**
 * @Author lishaohui
 * @Date 2023/5/24 23:27
 */
public class RateLimiterDemo {

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(0.5d);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.rangeClosed(0, 10).forEach(i -> executorService.submit(RateLimiterDemo::testLimiter));
    }

    private static void testLimiter() {
        System.out.println(currentThread() + " waiting " + RATE_LIMITER.acquire());
    }

}
