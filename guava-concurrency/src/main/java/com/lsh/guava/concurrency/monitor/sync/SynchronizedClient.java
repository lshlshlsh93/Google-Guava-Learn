package com.lsh.guava.concurrency.monitor.sync;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author lishaohui
 * @Date 2023/5/24 22:19
 */
@SuppressWarnings("all")
public class SynchronizedClient {

    public static void main(String[] args) {

        final SynchronizedDemo<Integer> sync = new SynchronizedDemo<>(10);
        final AtomicInteger counter = new AtomicInteger(0);

        IntStream.rangeClosed(1, 3).forEach((i) -> {
            new Thread(() -> {
                while (true) {
                    int data = counter.incrementAndGet();
                    Optional
                            .of(Thread.currentThread().getName() + " produce: " + data)
                            .ifPresent(System.out::println);
                    sync.put(data);
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }, "Producer-" + i).start();
        });

        IntStream.rangeClosed(1, 2).forEach((i) -> {
            new Thread(() -> {
                while (true) {
                    Integer data = sync.get();
                    Optional
                            .of(Thread.currentThread().getName() + " consumer: " + data)
                            .ifPresent(System.out::println);
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Consumer-" + i).start();
        });
    }

}
