package com.evc.freetime;


import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author rongli
 * @since 2019-06-25 09:50
 */
public class QueueTest {

    public static void main(String[] args) throws InterruptedException {
        testArrayBlockingQueue();
    }

    public static void testArrayBlockingQueue() throws InterruptedException {
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue(10);
        Thread put = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    boolean result = false;
                    try {
                       queue.put("" + System.currentTimeMillis());
                       result = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        result = false;
                    }
                    System.out.println(result);
                    try {
                        if (result) {
                            System.out.println("----- put 睡眠5");
                            Thread.currentThread().sleep(5);
                        }else {
                            System.out.println("----- put 睡眠5000");
                            Thread.currentThread().sleep(5000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        put.join();
        put.start();

        Thread get = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    String result = queue.poll();
                    System.out.println(result);
                    try {
                        if (result == null || "".equals(result)) {
                            System.out.println(">>>>>> get 睡眠5000");
                            Thread.currentThread().sleep(5000);
                        }else {
                            System.out.println(">>>>>> get 睡眠5");
                            Thread.currentThread().sleep(5);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        get.join();
        get.start();
        System.out.println("main end");
    }
}
