package com.lsh.guava.eventbus.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author lishaohui
 * @Date 2023/5/31 11:25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomSubscribe {

    String topic() default CustomEventBus.DEFAULT_TOPIC_NAME;

}
