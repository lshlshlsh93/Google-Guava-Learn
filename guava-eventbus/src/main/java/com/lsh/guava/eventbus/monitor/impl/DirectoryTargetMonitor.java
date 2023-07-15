package com.lsh.guava.eventbus.monitor.impl;

import com.google.common.eventbus.EventBus;

import com.lsh.guava.eventbus.events.FileChangeEvent;
import com.lsh.guava.eventbus.monitor.TargetMonitor;


import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * @Author lishaohui
 * @Date 2023/5/31 10:14
 */
public class DirectoryTargetMonitor implements TargetMonitor {

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path targetPath;

    private volatile boolean isStart = false;

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath) {
        this(eventBus, targetPath, "");
    }

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath, final String... more) {
        this.eventBus = eventBus;
        this.targetPath = Paths.get(targetPath, more);
    }

    @Override
    public void startMonitor() throws Exception {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.targetPath.register(watchService,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_CREATE
        );
        System.out.println("The directory " + targetPath + " is monitoring...");
        this.isStart = true;
        while (isStart) {
            WatchKey watchKey = null;
            try {
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event -> {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path context = (Path) event.context();
                    Path children = DirectoryTargetMonitor.this.targetPath.resolve(context);
                    eventBus.post(new FileChangeEvent(children, kind));
                });
            } catch (InterruptedException e) {
                this.isStart = false;
            } finally {
                if (watchKey != null) {
                    watchKey.reset();
                }
            }
        }
    }

    @Override
    public void stopMonitor() throws Exception {
        System.out.println("The directory " + targetPath + " monitor will be stop.");
        Thread.currentThread().interrupt();
        this.isStart = false;
        this.watchService.close();
        System.out.println("The directory " + targetPath + " monitor stop done.");
    }
}
