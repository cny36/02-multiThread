package com.cny.multithread.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 锁的可重入性测试
 */
@Slf4j
public class ReentrantLockTest {

    public static void main(String[] args) {
        //同一个线程可以重复获取到同一把锁
        new Thread(new MyRunnable()).start();
        new Thread(new MyRunnable()).start();
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            synchronized (MyRunnable.class) {
                log.info("线程：{}第一次进入同步代码块", Thread.currentThread().getName());
                synchronized (MyRunnable.class) {
                    log.info("线程：{}第二次进入同步代码块", Thread.currentThread().getName());
                }
            }
        }
    }
}
