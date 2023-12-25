package com.cny.multithread.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport测试
 * unpark()可以比park()先执行
 */
@Slf4j
public class LockSupportTest {

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("thread1执行park");
            LockSupport.park();

            log.info("thread1被唤醒");
        });

        thread1.start();

        log.info("thread1执行unpark");
        LockSupport.unpark(thread1);

    }
}
