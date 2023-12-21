package com.cny.multithread.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : chennengyuan
 */
@Slf4j
public class MyBlockingQueueTest {


    public static void main(String[] args) {
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);

        producer.setName("producer");
        consumer.setName("consumer");

        producer.start();
        consumer.start();
    }

    static class Store {
        private int maxSize;
        private List<Date> list;

        public Store() {
            this.maxSize = 10;
            list = new ArrayList<>();
        }

        @SneakyThrows
        public synchronized void put(Date date) {
            if (list.size() == maxSize) {
                log.info("list 满了 进入阻塞状态", list.size());
                this.wait();
            }
            list.add(date);
            log.info("list 新增了1条记录，当前list大小为：{}", list.size());
            this.notify();
        }

        @SneakyThrows
        public synchronized void get() {
            if (list.size() == 0) {
                log.info("list 空了 进入阻塞状态", list.size());
                this.wait();
            }
            Date date = list.remove(list.size() - 1);
            log.info("list 移除了1条记录 {}", date);
            this.notify();
        }
    }


    static class Producer extends Thread{

        private Store store;

        public Producer(Store store) {
            this.store = store;
        }

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                Thread.sleep(2000);
                store.put(new Date());
            }
        }
    }


    static class Consumer extends Thread{

        private Store store;

        public Consumer(Store store) {
            this.store = store;
        }

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                Thread.sleep(100);
                store.get();
            }
        }
    }
}
