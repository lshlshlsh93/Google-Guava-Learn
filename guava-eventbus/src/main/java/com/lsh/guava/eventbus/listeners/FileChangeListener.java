package com.lsh.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;

import com.lsh.guava.eventbus.events.FileChangeEvent;

/**
 * @Author lishaohui
 * @Date 2023/5/31 10:32
 */
public class FileChangeListener {

    @Subscribe
    public void onChange(FileChangeEvent event) {
        System.out.println(event.getPath() + "-" + event.getKind());
    }

}
