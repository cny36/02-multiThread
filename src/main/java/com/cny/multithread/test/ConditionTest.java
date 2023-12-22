package com.cny.multithread.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 测试
 */
@Slf4j
public class ConditionTest {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                lock.lock();
                log.info("{}进入阻塞状态", Thread.currentThread().getName());
                condition.await();
                log.info("{}被唤醒", Thread.currentThread().getName());
                lock.unlock();
            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                lock.lock();
                Thread.sleep(1000);
                log.info("{}处理完成，唤醒其他线程", Thread.currentThread().getName());
                condition.signal();
                lock.unlock();
            }
        }).start();
    }
}
