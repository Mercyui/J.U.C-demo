package com.mercy.demo.bqueue;

import com.mercy.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.CrossOrigin;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class BlockingQueueTest {

    /**
     * <p>
     * Java中阻塞队列的几种实现方式
     * 阻塞队列：阻塞队列与普通队列的区别在于，当队列是空的时，从队列中获取元素的操作将会被阻塞;
     * 或者当队列是满时，往队列里添加元素的操作会被阻塞。
     * </p>
     */
    @Test
    @CrossOrigin
    public void implBlockingQueue1() throws InterruptedException {
        MyBlockingQueue queue = new MyBlockingQueue(3);
        Thread t1 = new Thread(() -> {
            queue.enQueue("1");
            queue.enQueue("2");
            queue.enQueue("3");
        }, "T1");

        Thread t2 = new Thread(() -> {
            queue.enQueue("4");
            queue.enQueue("5");
            queue.enQueue("6");
        }, "T2");

        Thread t3 = new Thread(() -> {
            queue.enQueue("7");
            queue.enQueue("8");
            queue.enQueue("9");
        }, "T3");

        t1.start();
        t2.start();
        t3.start();

        // Thread.sleep(5000);
    }

    @Test
    public void implBlockingQueue2() throws InterruptedException {
        BoundedBuffer queue = new BoundedBuffer();
        Thread t1 = new Thread(() -> {
            try {
                queue.put("1");
                queue.put("2");
                queue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "T1");

        Thread t2 = new Thread(() -> {
            try {
                queue.put("4");
                queue.put("5");
                queue.put("6");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T2");

        Thread t3 = new Thread(() -> {
            try {
                queue.put("7");
                queue.put("8");
                queue.put("9");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T3");

        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(5000);
    }
}
