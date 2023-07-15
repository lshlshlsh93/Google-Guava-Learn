package com.lsh.guava.cache;

/**
 * @Author lishaohui
 * @Date 2023/6/9 22:57
 */
public interface LRUCache<K, V> {

    /**
     *  put element into the container
     * @param key key
     * @param value value
     */
    void put(K key, V value);

    /**
     * get element from the container
     * @param key key
     * @return element
     */
    V get(K key);

    /**
     * remove element from the container
     * @param key key
     */
    void remove(K key);

    /**
     * return the size
     */
    int size();

    /**
     * clear all element
     */
    void clear();

    /**
     * the limit size of container
     */
    int limit();

}
