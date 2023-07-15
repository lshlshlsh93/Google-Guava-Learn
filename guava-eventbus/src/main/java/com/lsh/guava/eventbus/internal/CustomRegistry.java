package com.lsh.guava.eventbus.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author lishaohui
 * @Date 2023/5/31 10:57
 * <pre>
 * topic1->subscriber-subscribe
 *       ->subscriber-subscribe
 *       ->subscriber-subscribe
 *       ->subscriber-subscribe
 * topic2->subscriber-subscribe
 *       ->subscriber-subscribe
 *       ->subscriber-subscribe
 *       ->subscriber-subscribe
 * </pre>
 */
class CustomRegistry {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> subscriberContainer = new ConcurrentHashMap<>();


    public void bind(Object subscriber) {
        List<Method> subscribeMethod = getSubscribeMethods(subscriber);
        subscribeMethod.forEach(method -> tierSubscriber(subscriber, method));
    }


    public void unbind(Object subscriber) {
        subscriberContainer.forEach((key, queue) -> {
            queue.forEach(sub -> {
                if (sub.getSubscribeObject() == subscriber) {
                    sub.setDisabled(true);
                }
            });
        });
    }

    public ConcurrentLinkedQueue<Subscriber> scanSubscriber(final String topic) {
        return subscriberContainer.get(topic);
    }


    private List<Method> getSubscribeMethods(Object subscriber) {
        final List<Method> methods = new ArrayList<>();
        Class<?> clazz = subscriber.getClass();
        while (clazz != null) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            Arrays
                    .stream(declaredMethods)
                    .filter(method -> method.isAnnotationPresent(CustomSubscribe.class)
                            && method.getParameterCount() == 1
                            && method.getModifiers() == Modifier.PUBLIC)
                    .forEach(methods::add);
            clazz = clazz.getSuperclass();
        }
        return methods;
    }

    private void tierSubscriber(Object subscriber, Method method) {
        final CustomSubscribe customSubscribe = method.getDeclaredAnnotation(CustomSubscribe.class);
        String topic = customSubscribe.topic();
        subscriberContainer.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue<>());
        subscriberContainer.get(topic).add(new Subscriber(subscriber, method));
    }


}
