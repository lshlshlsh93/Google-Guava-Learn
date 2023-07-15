package com.lsh.guava.cache.client;

import com.lsh.guava.cache.LRUCache;
import com.lsh.guava.cache.linked_list.LinkedListLRUCache;

/**
 * @Author lishaohui
 * @Date 2023/6/9 23:51
 */
public class LinkedListLRUCacheClient {

    public static void main(String[] args) {
        LRUCache<String, String> stringLRUCache = new LinkedListLRUCache<>(3);
        stringLRUCache.put("1", "1");
        stringLRUCache.put("2", "2");
        stringLRUCache.put("3", "3");
        System.out.println(stringLRUCache); // {1=1, 2=2, 3=3}
        stringLRUCache.put("4", "4");
        System.out.println(stringLRUCache); // {2=2, 3=3, 4=4}
        System.out.println(stringLRUCache.get("2")); // 2
        System.out.println(stringLRUCache); // {3=3, 4=4, 2=2}
    }
}
