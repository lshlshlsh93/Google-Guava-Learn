package com.lsh.guava.concurrency.monitor.guava;

import com.google.common.util.concurrent.Monitor;

import java.util.LinkedList;

/**
 * @Author lishaohui
 * @Date 2023/5/24 22:11
 */
public class GuavaMonitorDemo<V> {

    private final LinkedList<V> queue;

    private final int max;

    private final Monitor monitor = new Monitor();
    ;

    private final Monitor.Guard CAN_OFFER;

    private final Monitor.Guard CAN_TAKE;

    public GuavaMonitorDemo(int max) {
        this.queue = new LinkedList<>();
        this.max = max;
        CAN_OFFER = monitor.newGuard(() -> queue.size() < this.max);
        CAN_TAKE = monitor.newGuard(() -> !queue.isEmpty());
    }

    public void offer(V v) {
        try {
            // when can offer then enter
            monitor.enterWhen(CAN_OFFER);
            // add value
            queue.addLast(v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // leave
            monitor.leave();
        }
    }

    public V take() {
        V v;
        try {
            // when can take then enter
            monitor.enterWhen(CAN_TAKE);
            v = queue.removeFirst();
            return v;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // leave when do thing over
            monitor.leave();
        }
    }


}
