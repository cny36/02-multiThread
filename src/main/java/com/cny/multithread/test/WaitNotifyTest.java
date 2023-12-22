package com.cny.multithread.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * wait和notify测试
 */
@Slf4j
public class WaitNotifyTest {

    private static Object lock = new Object();

    @SneakyThrows
    public static void main(String[] args) {
        new WaitThread().start();
        Thread.sleep(2000);
        new NotifyThread().start();
    }

    private static class WaitThread extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            synchronized (lock) {
                log.info("{} 获取到锁", Thread.currentThread().getName());
                lock.wait();
                log.info("{} 被唤醒,继续执行", Thread.currentThread().getName());
            }
        }
    }

    private static class NotifyThread extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                log.info("{} 获取到锁", Thread.currentThread().getName());
                lock.notify();
                log.info("{} 唤醒线程", Thread.currentThread().getName());
            }
        }
    }
}
