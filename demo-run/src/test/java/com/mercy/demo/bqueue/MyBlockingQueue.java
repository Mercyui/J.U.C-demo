package com.mercy.demo.bqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * java 实现阻塞队列
 *
 * @author mercy create on 2019-2-10
 */
public class MyBlockingQueue {
    /**
     * capacity 默认10
     */
    private AtomicInteger capacity;

    private LinkedList<Object> list = new LinkedList<>();

    private static final Lock LOCK = new ReentrantLock();

    private static final Condition EN_QUEUE_CONDITION = LOCK.newCondition();

    private static final Condition DE_QUEUE_CONDITION = LOCK.newCondition();

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBlockingQueue.class);


    public MyBlockingQueue() {
        this.capacity = new AtomicInteger(10);
    }

    public MyBlockingQueue(int capacity) {
        this.capacity = new AtomicInteger(capacity);
    }

    private int capacity() {
        return capacity.get();
    }

    /**
     * 入队操作
     */
    public void enQueue(Object obj) {
        try {
            LOCK.lock();
            while (list.size() == capacity()) {
                try {
                    EN_QUEUE_CONDITION.await();
                } catch (InterruptedException e) {
                    LOGGER.error("Thread  [--{}] error:{}", Thread.currentThread().getName(), e);
                }
            }
            LOGGER.info("[--{}] enQueue:{}", Thread.currentThread().getName(), obj.toString());
            list.add(obj);
            DE_QUEUE_CONDITION.signal();
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 出队操作
     */
    public Object deQueue() {
        try {
            LOCK.lock();
            while (list.size() == 0) {
                try {
                    DE_QUEUE_CONDITION.await();
                } catch (InterruptedException e) {
                    LOGGER.error("Thread  [--{}] error:{}", Thread.currentThread().getName(), e);
                }
            }
            Object first = list.pollFirst();
            LOGGER.info("[--{}] deQueue:{}", Thread.currentThread().getName(), String.valueOf(first));
            EN_QUEUE_CONDITION.signal();
            return first;
        } finally {
            LOCK.unlock();
        }

    }
}