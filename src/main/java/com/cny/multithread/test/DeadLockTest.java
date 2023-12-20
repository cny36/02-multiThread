package com.cny.multithread.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 死锁测试
 */
@Slf4j
public class DeadLockTest {

    private static Object lokc1 = new Object();
    private static Object lokc2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lokc1) {
                log.info("线程1获取到lock1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("线程1等待lock2获取。。。。");
                synchronized (lokc2) {
                    log.info("线程1获取到lock2");
                }
            }
        }).start();


        new Thread(() -> {
            synchronized (lokc2) {
                log.info("线程2获取到lock2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("线程2等待lock1获取。。。。");
                synchronized (lokc1) {
                    log.info("线程2获取到lock1");
                }
            }
        }).start();

    }
}
