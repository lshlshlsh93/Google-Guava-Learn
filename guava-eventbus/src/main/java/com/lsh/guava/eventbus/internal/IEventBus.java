package com.lsh.guava.eventbus.internal;


/**
 * @Author lishaohui
 * @Date 2023/5/31 10:54
 */
public interface IEventBus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

    void post(Object event, String topic);

    void close();

    String getEventbusName();

}
