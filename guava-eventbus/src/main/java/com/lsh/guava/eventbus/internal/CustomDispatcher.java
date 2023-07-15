package com.lsh.guava.eventbus.internal;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @Author lishaohui
 * @Date 2023/5/31 11:07
 */
public class CustomDispatcher {

    private final Executor executor;

    private final CustomEventExceptionHandler exceptionHandler;

    public static final Executor SEQUENCE_EXECUTOR_SERVICE = SequenceExecutorService.instance;

    public static final Executor PER_THREAD_EXECUTOR_SERVICE = PerThreadExecutorService.instance;

    private CustomDispatcher(Executor executor, CustomEventExceptionHandler exceptionHandler) {
        this.executor = executor;
        this.exceptionHandler = exceptionHandler;
    }

    public void dispatch(CustomEventBus eventBus, CustomRegistry registry, Object event, String topic) {
        ConcurrentLinkedQueue<Subscriber> subscribers = registry.scanSubscriber(topic);
        if (null == subscribers) {
            if (exceptionHandler != null) {
                exceptionHandler.handle(
                        new IllegalArgumentException("The topic not bind yet."),
                        new BaseEventContext(eventBus.getEventbusName(), null, event)
                );
            }
            return;
        }
        subscribers.stream()
                .filter(subscriber -> !subscriber.isDisabled())
                .filter(subscriber -> {
                    Method subscribeMethod = subscriber.getSubscribeMethod();
                    Class<?> clazz = subscribeMethod.getParameterTypes()[0];
                    return (clazz.isAssignableFrom(event.getClass()));
                }).forEach(subscriber -> invokeSubscribeMethod(subscriber, event, eventBus));
    }

    private void invokeSubscribeMethod(Subscriber subscriber, Object event, CustomEventBus eventBus) {
        Method subscribeMethod = subscriber.getSubscribeMethod();
        Object subscribeObject = subscriber.getSubscribeObject();
        executor.execute(() -> {
            try {
                subscribeMethod.invoke(subscribeObject, event);
            } catch (Exception e) {
                if (null != exceptionHandler) {
                    exceptionHandler.handle(e, new BaseEventContext(eventBus.getEventbusName(), subscriber, event));
                }
            }
        });
    }


    public void close() {
        if (executor instanceof ExecutorService executorService) executorService.shutdown();
    }

    static CustomDispatcher newDispatcher(CustomEventExceptionHandler exceptionHandler, Executor executor) {
        return new CustomDispatcher(executor, exceptionHandler);
    }

    static CustomDispatcher sequenceDispatcher(CustomEventExceptionHandler exceptionHandler) {
        return new CustomDispatcher(SEQUENCE_EXECUTOR_SERVICE, exceptionHandler);
    }

    static CustomDispatcher perThreadDispatcher(CustomEventExceptionHandler exceptionHandler) {
        return new CustomDispatcher(PER_THREAD_EXECUTOR_SERVICE, exceptionHandler);
    }

    private static class SequenceExecutorService implements Executor {
        private static final SequenceExecutorService instance = new SequenceExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private static class PerThreadExecutorService implements Executor {
        private static final PerThreadExecutorService instance = new PerThreadExecutorService();

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    private static class BaseEventContext implements CustomEventContext {

        private final String eventbusName;

        private final Subscriber subscriber;

        private final Object event;

        private BaseEventContext(String eventbusName, Subscriber subscriber, Object event) {
            this.eventbusName = eventbusName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSource() {
            return this.eventbusName;
        }

        @Override
        public Object getSubscriber() {
            return subscriber != null ? subscriber.getSubscribeObject() : null;
        }

        @Override
        public Method getSubscribeMethod() {
            return subscriber != null ? subscriber.getSubscribeMethod() : null;
        }

        @Override
        public Object getEvent() {
            return this.event;
        }
    }

}
