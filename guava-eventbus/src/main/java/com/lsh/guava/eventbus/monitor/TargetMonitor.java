package com.lsh.guava.eventbus.monitor;

/**
 * @Author lishaohui
 * @Date 2023/5/31 10:13
 */
public interface TargetMonitor {

    void startMonitor() throws Exception;

    void stopMonitor() throws Exception;

}
