package com.lee.free.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author rongli
 * @since 2019-09-17 11:33
 */
public class TwinsLockTest {

    public static void main(String[] args) {
        new TwinsLockTest().testTwinsLock();
    }
    public void testTwinsLock() {
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    // 获取锁
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName());
                        SleepUtils.second(1);
                    }
                    finally {
                        // 释放锁
                        lock.unlock();
                    }
                }
            }
        }
        // 开10个线程运行worker, 如果没有锁，应该是几乎同时很快完成
        // 但 TwinsLock 只允许同时有两个线程获得锁运行
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        // 每隔1s换行
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println();
        }

    }
}

/**
 * 双资源锁
 */
class TwinsLock implements Lock {
    private final Sync sync = new Sync(2);
    private static final class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -8540764104913403569L;

        Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("锁资源数不能为负数~");
            }
            // 调用 AQS 设置资源总数，备用
            setState(count);
        }

        @Override
        public int tryAcquireShared(int reduceCount) {
            // cas 获取锁
            // 由 AQS 的 acquireShared -> doAcquireShared 调用
            for (; ; ) {
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        public boolean tryReleaseShared(int returnCount) {
            // cas 释放锁
            // 由AQS releaseShared -> doReleaseShared 调用
            for (; ; ) {
                int current = getState();
                int newState = current + returnCount;
                if (compareAndSetState(current, newState)) {
                    return true;
                }
            }
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    // 忽略，如要实现，直接调用 AQS
    @Override
    public boolean tryLock() {
        return false;
    }

    // 忽略，如要实现，直接调用 AQS
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    // 忽略，如要实现，直接调用 AQS
    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    // 忽略，如要实现，直接调用 AQS
    @Override
    public Condition newCondition() {
        return null;
    }
}

// 睡眠工具类
class SleepUtils {
    public static void second(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
