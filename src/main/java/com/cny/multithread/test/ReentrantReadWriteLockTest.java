package com.cny.multithread.test;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 * <p>
 * 不允许存在读锁和写锁同时被多个线程占用的情况
 * 要么多个线程占用一个读锁，要么一个线程占用一个写锁
 */
@Slf4j
public class ReentrantReadWriteLockTest {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) {
        new Thread(() -> read(), "r0").start();
        new Thread(() -> read(), "r1").start();
        new Thread(() -> write(), "r2").start();
        new Thread(() -> write(), "r3").start();

    }


    private static void read() {
        try {
            readLock.lock();
            log.info("{} 获取到读锁", Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (Exception e) {
        } finally {
            log.info("{} 释放读锁", Thread.currentThread().getName());
            readLock.unlock();
        }
    }


    private static void write() {
        try {
            writeLock.lock();
            log.info("{} 获取到写锁", Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (Exception e) {
        } finally {
            log.info("{} 释放写锁", Thread.currentThread().getName());
            writeLock.unlock();
        }
    }


    /**
     * 采用HashMap+读写锁实现线程安全的缓存组件
     */
    private static class MyCache {
        private static Map<String, Object> myMapCache = new HashMap<>();

        private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        public static Object get(String key) {
            readLock.lock();
            try {
                return myMapCache.get(key);
            } finally {
                readLock.unlock();
            }
        }

        public static void put(String key, Object value) {
            writeLock.lock();
            try {
                myMapCache.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }

        public static void clear() {
            writeLock.lock();
            try {
                myMapCache.clear();
            } finally {
                writeLock.unlock();
            }
        }

    }
}
