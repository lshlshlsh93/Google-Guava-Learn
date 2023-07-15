package com.lsh.guava.cache.client;

import com.lsh.guava.cache.soft_reference.SoftReferenceLRUCache;

import java.util.concurrent.TimeUnit;

/**
 * @Author lishaohui
 * @Date 2023/6/10 11:08
 */
public class SoftReferenceLRUCacheClient {

    public static void main(String[] args) throws InterruptedException {
        final SoftReferenceLRUCache<String, byte[]> softReferenceLRUCache =
                new SoftReferenceLRUCache<>(100);
        for (int i = 0; i < 100; i++) {
            softReferenceLRUCache.put(String.valueOf(i), new byte[1024 * 1024 * 2]);
            TimeUnit.MILLISECONDS.sleep(600);
            System.out.println("The " + i + " entity is cached.");
        }
    }

}
