package com.cny.multithread.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * 实现多等一
 */
@Slf4j
public class CountDownLatchTest {

    @SneakyThrows
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    log.info("用户：{} 排队中...", Thread.currentThread().getName());
                    countDownLatch.await();
                    log.info("用户：{} 开始抢购商品", Thread.currentThread().getName());
                }
            }).start();
        }

        Thread.sleep(5000);

        countDownLatch.countDown();
        log.info("商品秒杀时间到");
    }
}
