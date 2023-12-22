package com.cny.multithread.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;

@Slf4j
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> log.info("平多多3人拼团成功"));

        for (int i = 0; i < 13; i++) {
            new Thread(new PinTuanTask(i, cyclicBarrier)).start();
        }
    }


    static class PinTuanTask implements Runnable {

        private int id;
        private CyclicBarrier cyclicBarrier;

        public PinTuanTask(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
        }

        @SneakyThrows
        @Override
        public void run() {
            Thread.sleep((long) (Math.random()*10000));
            log.info("{} 加入拼团。。。", id);
            cyclicBarrier.await();
        }
    }
}
