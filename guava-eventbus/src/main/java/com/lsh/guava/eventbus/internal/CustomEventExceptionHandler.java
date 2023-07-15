package com.lsh.guava.eventbus.internal;

/**
 * @Author lishaohui
 * @Date 2023/5/31 11:03
 */
public interface CustomEventExceptionHandler {

    void handle(Throwable cause, CustomEventContext context);

}
