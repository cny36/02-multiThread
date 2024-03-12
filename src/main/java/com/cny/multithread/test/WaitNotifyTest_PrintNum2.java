package com.cny.multithread.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2个线程交替打印即偶数
 */
@Slf4j
public class WaitNotifyTest_PrintNum2 {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition jiCondition = lock.newCondition();
    private final Condition ouCondition = lock.newCondition();

    private boolean isJi = true;

    @SneakyThrows
    public void printJI() {
        for (int i = 1; i<=10000; i += 2) {
            lock.lock();
            if (!isJi) {
                jiCondition.await();
            }
            log.info("{}:{}", Thread.currentThread().getName(), i);
            isJi = false;
            ouCondition.signal();
            lock.unlock();
        }
    }


    @SneakyThrows
    public void printOU() {
        for (int i = 2; i<=10000; i += 2) {
            lock.lock();
            if (isJi) {
                ouCondition.await();
            }
            log.info("{}:{}", Thread.currentThread().getName(), i);
            isJi = true;
            jiCondition.signal();
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        WaitNotifyTest_PrintNum2 test = new WaitNotifyTest_PrintNum2();
        new Thread(test::printJI, "奇数-").start();
        new Thread(test::printOU, "偶数-").start();
    }


}
