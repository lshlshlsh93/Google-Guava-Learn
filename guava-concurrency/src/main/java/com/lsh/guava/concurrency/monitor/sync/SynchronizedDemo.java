package com.lsh.guava.concurrency.monitor.sync;

import java.util.LinkedList;

/**
 * @Author lishaohui
 * @Date 2023/5/24 22:04
 */
public class SynchronizedDemo<V> {

    private final LinkedList<V> queue;

    private final int max;

    public SynchronizedDemo(int max) {
        this.max = max;
        this.queue = new LinkedList<>();
    }

    public void put(V v) {
        synchronized (queue) {
            while (queue.size() >= max) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(v);
            queue.notifyAll();
        }
    }

    public V get() {
        V v;
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            v = queue.removeFirst();
            queue.notifyAll();
            return v;
        }
    }


}
