package com.lsh.guava.concurrency.ratelimiter.leaky_bucket;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

/**
 * @Author lishaohui
 * @Date 2023/5/26 15:04
 */
public class LeakyBucket<T> {

    private final ConcurrentLinkedQueue<T> container = new ConcurrentLinkedQueue<>();

    private final int BUCKET_LIMIT = 1000;

    private final RateLimiter limiter = RateLimiter.create(10);

    private final Monitor offerMonitor = new Monitor();

    private final Monitor pullMonitor = new Monitor();

    public void submit(T data) {
        // 当桶不满时可以放数据
        if (offerMonitor.enterIf(offerMonitor.newGuard(() -> container.size() < BUCKET_LIMIT))) {
            try {
                container.offer(data);
                System.out.println(currentThread().getName()
                        + " submit data : " + data + ", current size: " + container.size());
            } finally {
                offerMonitor.leave();
            }
        } else {
            throw new IllegalStateException("The bucket is full.");
        }
    }

    public void takeThenConsumer(Consumer<T> consumer) {
        // 当桶不为空时可以拿数据
        if (pullMonitor.enterIf(pullMonitor.newGuard(() -> !container.isEmpty()))) {
            try {
                System.out.println(currentThread().getName() + " waiting : " + limiter.acquire());
                consumer.accept(container.poll());
            } finally {
                pullMonitor.leave();
            }
        }

    }

}
