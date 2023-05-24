package com.lsh.guava.concurrency.monitor.guava;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author lishaohui
 * @Date 2023/5/24 22:58
 */
@SuppressWarnings("all")
public class GuavaMonitorClient {

    public static void main(String[] args) {

        final GuavaMonitorDemo<Integer> gm = new GuavaMonitorDemo<>(10);
        final AtomicInteger counter = new AtomicInteger(0);

        IntStream.rangeClosed(1, 4).forEach(i -> {
            new Thread(() -> {
                for (; ; ) {
                    int value = counter.incrementAndGet();
                    Optional
                            .of(Thread.currentThread().getName() + " produce: " + value)
                            .ifPresent(System.out::println);
                    gm.offer(value);
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
                for (; ; ) {
                    Integer value = gm.take();
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
