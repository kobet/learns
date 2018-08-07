/**
 * @copyright remark holdings
 */
package com.example.learnjdk.locks;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author kobe_t
 * @date 2018/7/19 16:14
 */
public class ReadWriteMap<K, V> {

    private final Map<K, V> map;

    /**
     * 读写锁：读，写是互斥
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 读锁-共享
     */
    private final Lock read = lock.readLock();

    /**
     * 写锁-独占
     */
    private final Lock write = lock.writeLock();

    public ReadWriteMap(Map<K, V> map) {
        this.map = map;
    }

    public void put(K key, V value) {
        write.lock();
        try {
            map.put(key, value);
        } finally {
            write.unlock();
        }
    }

    public V get(K key) {
        read.lock();
        try {
            return map.get(key);
        } finally {
            read.unlock();
        }
    }
}
