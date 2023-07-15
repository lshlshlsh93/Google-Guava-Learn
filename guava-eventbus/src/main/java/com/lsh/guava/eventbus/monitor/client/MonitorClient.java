package com.lsh.guava.eventbus.monitor.client;

import com.google.common.eventbus.EventBus;

import com.lsh.guava.eventbus.listeners.FileChangeListener;
import com.lsh.guava.eventbus.monitor.TargetMonitor;
import com.lsh.guava.eventbus.monitor.impl.DirectoryTargetMonitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author lishaohui
 * @Date 2023/5/31 10:40
 */
public class MonitorClient {

    public static void main(String[] args) throws Exception {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FileChangeListener());

        TargetMonitor monitor = new DirectoryTargetMonitor(eventBus,
                "D:\\Java Module\\Google-Guava-Learn\\guava-eventbus\\src\\main\\resources\\txt");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            try {
                monitor.stopMonitor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 2, TimeUnit.MINUTES);
        executorService.shutdown();
        monitor.startMonitor();
    }

}
