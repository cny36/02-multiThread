package com.cny.multithread.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 原子性测试
 *
 * @author : chennengyuan
 */
@Slf4j
public class AtomicityTest {

    //共享变量 多个线程操作需要加锁
    private static int count = 0;

    @SneakyThrows
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++;
            }
            log.info("t1 自增结束");
        }, "t1");


        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                count++;
            }
            log.info("t2 自增结束");
        }, "t2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        log.info("累加后的count={}", count);
    }
}
