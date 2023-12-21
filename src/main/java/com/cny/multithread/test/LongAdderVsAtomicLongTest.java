package com.cny.multithread.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 测试原子类 LongAdder vs AtomicLong
 */
@Slf4j
public class LongAdderVsAtomicLongTest {

    @SneakyThrows
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();

        LongAdder count = new LongAdder();
        List<Thread> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable1_LongAdder(count));
            thread.start();
            list.add(thread);
        }
        for (Thread thread : list) {
            thread.join();
        }

        long end = System.currentTimeMillis();

        log.info("count:{}", count.sum());
        log.info("cost time:{}", (end - begin));
    }


    static class Runnable1_LongAdder implements Runnable {

        private LongAdder longAdder;

        private Runnable1_LongAdder(LongAdder longAdder) {
            this.longAdder = longAdder;
        }

        @Override
        public void run() {
            for (int i = 0; i < 200000; i++) {
                longAdder.increment();
            }
        }
    }


    static class Runnable1_AtomicLong implements Runnable {

        private AtomicLong atomicLong;

        private Runnable1_AtomicLong(AtomicLong atomicLong) {
            this.atomicLong = atomicLong;
        }

        @Override
        public void run() {
            for (int i = 0; i < 200000; i++) {
                atomicLong.incrementAndGet();
            }
        }
    }
}
