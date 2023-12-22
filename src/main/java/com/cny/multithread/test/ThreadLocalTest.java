package com.cny.multithread.test;

import lombok.extern.slf4j.Slf4j;

/**
 * ThreadLocal测试
 * 每个线程独一份互不影响
 */
@Slf4j
public class ThreadLocalTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(() -> {
            threadLocal.set("1111");
            System.out.println("t1线程：" + threadLocal.get());
        }, "t1").start();

        new Thread(() -> {
            threadLocal.set("2222");
            System.out.println("t2线程：" + threadLocal.get());
        }, "t2").start();

        threadLocal.set("3333");
        System.out.println("主线程：" + threadLocal.get());

        threadLocal.remove();
        System.out.println("主线程：" + threadLocal.get());

    }
}
