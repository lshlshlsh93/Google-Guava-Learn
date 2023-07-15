package com.lsh.guava.eventbus.internal;

import java.lang.reflect.Method;

/**
 * @Author lishaohui
 * @Date 2023/5/31 11:29
 */
public class Subscriber {

    private final Object subscribeObject;

    private final Method subscribeMethod;

    private boolean isDisabled = false;

    public Subscriber(Object subscribeObject, Method subscribeMethod) {
        this.subscribeObject = subscribeObject;
        this.subscribeMethod = subscribeMethod;
    }

    public Method getSubscribeMethod() {
        return subscribeMethod;
    }

    public Object getSubscribeObject() {
        return subscribeObject;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}
