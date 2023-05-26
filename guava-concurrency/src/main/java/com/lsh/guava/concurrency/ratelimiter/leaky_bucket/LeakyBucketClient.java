package com.lsh.guava.concurrency.ratelimiter.leaky_bucket;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/**
 * @Author lishaohui
 * @Date 2023/5/26 15:14
 */
public class LeakyBucketClient {

    public static void main(String[] args) {

        final LeakyBucket<Long> bucket = new LeakyBucket<>();
        final AtomicLong DATA_CREATOR = new AtomicLong(0L);

        IntStream.rangeClosed(1, 5).forEach(i -> {
            new Thread(() -> {
                while (true) {
                    long data = DATA_CREATOR.incrementAndGet();
                    bucket.submit(data);
                    try {
                        TimeUnit.SECONDS.sleep(2L);
                    } catch (Exception e) {
                        if (e instanceof IllegalStateException) {
                            System.out.println(e.getMessage());
                        }
                        e.printStackTrace();
                    }
                }
            }, "Producer-" + i).start();
        });
        IntStream.rangeClosed(1, 5).forEach(i ->
                new Thread(() -> {
                    while (true) {
                        bucket.takeThenConsumer(x -> System.out.println(currentThread().getName() + " W " + x));
                    }
                }, "Consumer-" + i).start());

    }

}
