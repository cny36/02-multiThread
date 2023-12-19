package com.cny.multithread.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * 可见性测试
 *
 * @author : chennengyuan
 */
@Slf4j
public class VisibilityTest {

    //共享变量
    private static boolean flag = false;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                sleep(1000);
                flag = true;
                log.info("t1 set flag=true");
            }
        }, "t1").start();
        log.info("t1 start");


        new Thread(new Runnable() {
            @Override
            public void run() {
                //当flag不用volatile修饰时，t2线程无法读取到修改后的flag的值
                while (!flag) {
                }
                log.info("t2 read flag={}", flag);
            }
        }).start();
        log.info("t2 start");

    }

}
