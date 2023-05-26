package com.lsh.guava.concurrency.ratelimiter.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/**
 * @Author lishaohui
 * @Date 2023/5/24 23:33
 */
public class SemaphoreDemo {

    private static final Semaphore SEMAPHORE = new Semaphore(4);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.rangeClosed(0,10).forEach(i->executorService.submit(SemaphoreDemo::testSemaphore));
    }

    private static void testSemaphore() {
        try {
            SEMAPHORE.acquire();
            System.out.println(currentThread() + " is coming and do work.");
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            SEMAPHORE.release();
            System.out.println(currentThread() + " release the semaphore.");
        }
    }


}
