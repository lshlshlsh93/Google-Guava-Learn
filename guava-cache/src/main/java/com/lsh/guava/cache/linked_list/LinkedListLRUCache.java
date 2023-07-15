package com.lsh.guava.cache.linked_list;

import com.google.common.base.Preconditions;

import com.lsh.guava.cache.LRUCache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Author lishaohui
 * @Date 2023/6/9 23:35
 */
public class LinkedListLRUCache<K, V> implements LRUCache<K, V> {

    private final int limit;

    private final LinkedList<K> keys = new LinkedList<>();

    private final Map<K, V> cache = new HashMap<>();

    public LinkedListLRUCache(int limit) {
        this.limit = limit;
    }


    @Override
    public void put(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        if (keys.size() >= limit) {
            // if size >= limit, remove the head element
            K oldKey = keys.removeFirst();
            // remove from the map
            cache.remove(oldKey);
        }
        keys.addLast(key);
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        // judge whether remove success, if success then exist is true,otherwise is false.
        boolean exist = keys.remove(key);
        if (!exist) {
            return null;
        }
        keys.addLast(key);
        return cache.get(key);
    }

    @Override
    public void remove(K key) {
        keys.remove(key);
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public void clear() {
        this.keys.clear();
        this.cache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        for (K k : keys) {
            builder.append(k).append("=").append(cache.get(k)).append(";");
        }
        return builder.toString();
    }
}
