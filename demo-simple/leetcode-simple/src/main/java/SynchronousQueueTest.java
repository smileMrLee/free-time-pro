import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lirong
 * @createTime 2020年11月18日 17:30
 */
public class SynchronousQueueTest {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new SynchronousQueue<>();
        AtomicInteger count = new AtomicInteger(10);
        Thread product = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (count.decrementAndGet() > 0) {
                    try {
                        boolean result = queue.offer(count.intValue(), 2, TimeUnit.SECONDS);
                        System.out.println("放入:" + count.intValue());
                        Thread.currentThread().sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }
        });
        Thread consumer = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (count.get() > 0) {
                    try {
                        System.out.println("取得:" + queue.poll(2, TimeUnit.SECONDS));
                        Thread.currentThread().sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }
        });
        Thread monitor = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    System.out.println("队列长度：" + queue.size());
                    try {
                        Thread.currentThread().sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        product.start();
        consumer.start();
        monitor.start();
        System.out.println("end");

    }
}
