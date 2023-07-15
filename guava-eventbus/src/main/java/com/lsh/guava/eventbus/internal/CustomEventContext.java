package com.lsh.guava.eventbus.internal;

import java.lang.reflect.Method;

/**
 * @Author lishaohui
 * @Date 2023/5/31 11:04
 */
public interface CustomEventContext {

    String getSource();

    Object getSubscriber();

    Method getSubscribeMethod();

    Object getEvent();

}
