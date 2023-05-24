package com.lsh.guava.concurrency.monitor.reentrantlock;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author lishaohui
 * @Date 2023/5/24 21:55
 */
public class ReentrantLockDemo<V> {

    private final ReentrantLock LOCK = new ReentrantLock();

    // full condition
    private final Condition FULL_CONDITION = LOCK.newCondition();
    // empty condition
    private final Condition EMPTY_CONDITION = LOCK.newCondition();
    // queue
    private final LinkedList<V> queue;
    // max
    private final int max;

    public ReentrantLockDemo(int max) {
        this.queue = new LinkedList<>();
        this.max = max;
    }

    public void put(V v) {
        try {
            LOCK.lock();
            while (queue.size() >= max) {
                FULL_CONDITION.await();
            }
            queue.addLast(v);
            EMPTY_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    public V get() {
        V v;
        try {
            LOCK.lock();
            while (queue.isEmpty()) {
                EMPTY_CONDITION.await();
            }
            v = queue.removeFirst();
            FULL_CONDITION.signalAll();
            return v;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }


}
