package com.lsh.guava.cache.linked_hash;

import com.google.common.base.Preconditions;

import com.lsh.guava.cache.LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author lishaohui
 * @Date 2023/6/9 23:00
 */

/**
 * This class is not the thread-safe class.
 *
 * @param <K>
 * @param <V>
 */
public class LinkedHashLRUCache<K, V> implements LRUCache<K, V> {

    private final int limit;

    private final InternalLRUCache<K, V> internalLRUCache;

    public LinkedHashLRUCache(int limit) {
        Preconditions.checkArgument(limit > 0, "The limit should bigger than zero.");
        this.limit = limit;
        this.internalLRUCache = new InternalLRUCache<>(limit);
    }

    @Override
    public void put(K key, V value) {
        this.internalLRUCache.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.internalLRUCache.get(key);
    }

    @Override
    public void remove(K key) {
        this.internalLRUCache.remove(key);
    }

    @Override
    public int size() {
        return this.internalLRUCache.size();
    }

    @Override
    public void clear() {
        this.internalLRUCache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        return internalLRUCache.toString();
    }

    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int limit;

        public InternalLRUCache(int limit) {
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > limit;
        }
    }

}
