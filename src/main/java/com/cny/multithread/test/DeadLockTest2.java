package com.cny.multithread.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 死锁测试2 加上了超时时间
 */
@Slf4j
public class DeadLockTest2 {

    private static Lock lokc1 = new ReentrantLock();
    private static Lock lokc2 = new ReentrantLock();

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                if (lokc1.tryLock(2000, TimeUnit.MICROSECONDS)) {
                    log.info("线程1获取到lock1");
                    Thread.sleep(1000);
                    if (lokc2.tryLock(2000, TimeUnit.MICROSECONDS)) {
                        log.info("线程1获取到lock2");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }).start();


        new Thread(() -> {
            try {
                if (lokc2.tryLock(2000, TimeUnit.MICROSECONDS)) {
                    log.info("线程2获取到lock2");
                    Thread.sleep(1000);
                    if (lokc1.tryLock(2000, TimeUnit.MICROSECONDS)) {
                        log.info("线程2获取到lock1");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }).start();

    }
}
