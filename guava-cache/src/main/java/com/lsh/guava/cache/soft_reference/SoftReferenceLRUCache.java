package com.lsh.guava.cache.soft_reference;

import com.lsh.guava.cache.LRUCache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author lishaohui
 * @Date 2023/6/10 11:00
 */
public class SoftReferenceLRUCache<K, V> implements LRUCache<K, V> {

    private final int limit;

    private final InternalLRUCache<K, V> internalLRUCache;

    public SoftReferenceLRUCache(int limit) {
        this.limit = limit;
        this.internalLRUCache = new InternalLRUCache<>(limit);
    }


    @Override
    public void put(K key, V value) {
        this.internalLRUCache.put(key, new SoftReference<>(value));
    }

    @Override
    public V get(K key) {
        SoftReference<V> softReference = this.internalLRUCache.get(key);
        if (null == softReference) return null;
        return softReference.get();
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

    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, SoftReference<V>> {
        private final int limit;

        public InternalLRUCache(int limit) {
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, SoftReference<V>> eldest) {
            return this.size() > limit;
        }
    }

}
