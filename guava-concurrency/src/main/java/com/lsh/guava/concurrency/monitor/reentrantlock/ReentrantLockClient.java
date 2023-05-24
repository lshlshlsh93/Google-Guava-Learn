package com.lsh.guava.concurrency.monitor.reentrantlock;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author lishaohui
 * @Date 2023/5/24 22:34
 */
@SuppressWarnings("all")
public class ReentrantLockClient {

    public static void main(String[] args) {

        final ReentrantLockDemo<Integer> reentrantLock = new ReentrantLockDemo<>(10);
        final AtomicInteger counter = new AtomicInteger(0);
        IntStream.rangeClosed(1, 3).forEach(i -> {
            new Thread(() -> {
                while (true) {
                    int value = counter.incrementAndGet();
                    Optional
                            .of(Thread.currentThread().getName() + " produce: " + value)
                            .ifPresent(System.out::println);
                    reentrantLock.put(value);
                    try {
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Producer-" + i).start();
        });
        IntStream.rangeClosed(1, 2).forEach(i -> {
            new Thread(() -> {
                while (true) {
                    Integer value = reentrantLock.get();
                    Optional
                            .of(Thread.currentThread().getName() + " consume: " + value)
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
