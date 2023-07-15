package com.lsh.guava.eventbus.internal;

import java.util.concurrent.Executor;

/**
 * @Author lishaohui
 * @Date 2023/5/31 10:56
 */
public class CustomEventBus implements IEventBus {

    private static final String DEFAULT_EVENTBUS_NAME = "default";

    public static final String DEFAULT_TOPIC_NAME = "default-topic";

    private final String eventbusName;

    private final CustomRegistry registry = new CustomRegistry();

    private final CustomDispatcher dispatcher;

    private final CustomEventExceptionHandler exceptionHandler;

    public CustomEventBus() {
        this(DEFAULT_EVENTBUS_NAME, null, CustomDispatcher.SEQUENCE_EXECUTOR_SERVICE);
    }

    public CustomEventBus(String eventbusName) {
        this(eventbusName, null, CustomDispatcher.SEQUENCE_EXECUTOR_SERVICE);
    }
    public CustomEventBus(CustomEventExceptionHandler exceptionHandler) {
        this(DEFAULT_EVENTBUS_NAME, exceptionHandler, CustomDispatcher.SEQUENCE_EXECUTOR_SERVICE);
    }

    public CustomEventBus(String eventbusName, CustomEventExceptionHandler exceptionHandler, Executor executor) {
        this.eventbusName = eventbusName;
        this.exceptionHandler = exceptionHandler;
        this.dispatcher = CustomDispatcher.newDispatcher(exceptionHandler, executor);
    }

    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        this.registry.unbind(subscriber);
    }

    @Override
    public void post(Object event) {
        this.post(event, DEFAULT_TOPIC_NAME);
    }

    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this, registry, event, topic);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getEventbusName() {
        return this.eventbusName;
    }

}
