package com.lsh.guava.concurrency.ratelimiter.token_bucket;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author lishaohui
 * @Date 2023/5/26 15:49
 */
public class TokenBucket {

    private final AtomicInteger phoneNumbers = new AtomicInteger(0);

    private static final int MAX_REQUEST = 100;

    private final RateLimiter rateLimiter = RateLimiter.create(10);

    private final int saleLimit;

    public TokenBucket() {
        this(MAX_REQUEST);
    }

    public TokenBucket(int saleLimit) {
        this.saleLimit = saleLimit;
    }

    public int purchase() {
        Stopwatch started = Stopwatch.createStarted();
        boolean isSuccess = rateLimiter.tryAcquire(10, TimeUnit.SECONDS);
        if (isSuccess) {
            if (phoneNumbers.get() >= saleLimit) {
                throw new IllegalStateException("Not any more phone can be sale, please wait to next time.");
            }
            int phoneNo = phoneNumbers.getAndIncrement();
            handleOrder();
            System.out.println(Thread.currentThread() + "user get the phone: " + phoneNo + ", ELT: " + started.stop());
            return phoneNo;
        } else {
            started.stop();
            throw new RuntimeException("Sorry, occur exception to buy phone.");
        }
    }

    private void handleOrder() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
