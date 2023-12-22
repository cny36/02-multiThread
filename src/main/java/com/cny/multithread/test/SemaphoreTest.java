package com.cny.multithread.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;


@Slf4j
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    semaphore.acquire();
                    log.info("{} 获取到信号量", Thread.currentThread().getName());

                    Thread.sleep(2000);

                    log.info("{} 释放信号量", Thread.currentThread().getName());
                    semaphore.release();
                }
            }).start();
        }
    }
}
