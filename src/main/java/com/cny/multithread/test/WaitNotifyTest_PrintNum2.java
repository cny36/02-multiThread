package com.cny.multithread.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 2个线程交替打印即偶数
 */
@Slf4j
public class WaitNotifyTest_PrintNum2 {

    private static Object lock = new Object();

    private static int num = 1;

    public static void main(String[] args) {
        new Thread(() -> {
            while (num < 100) {
                synchronized (lock) {
                    if (num % 2 != 0) {
                        log.info("{}", num);
                        num++;

                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }, "奇数线程").start();

        new Thread(() -> {
            while (num <= 100) {
                synchronized (lock) {
                    if (num % 2 == 0) {
                        log.info("{}", num);
                        num++;

                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }, "偶数线程").start();
    }
}
