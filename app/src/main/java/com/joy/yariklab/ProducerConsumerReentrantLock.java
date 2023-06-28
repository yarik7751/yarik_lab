package com.joy.yariklab;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerReentrantLock<T> {

    private static final int BUFFER_MAX_SIZE = 2;

    private final ArrayDeque<T> buffer = new ArrayDeque<>(BUFFER_MAX_SIZE);

    private final ReentrantLock locker = new ReentrantLock();

    private final Condition produceCondition = locker.newCondition();
    private final Condition consumeCondition = locker.newCondition();

    public void produce(T item) throws InterruptedException {
        try {
            locker.lock();

            while (buffer.size() == BUFFER_MAX_SIZE) {
                System.out.println("wait produce");
                produceCondition.await();
            }

            buffer.push(item);
            System.out.println("added value=" + item);
            consumeCondition.signalAll();
        } finally {
            locker.unlock();
        }
    }

    public T consume() throws InterruptedException {
        try {
            locker.lock();

            while (buffer.size() == 0) {
                System.out.println("wait consume");
                consumeCondition.await();
            }
            T item = buffer.pop();
            System.out.println("got value=" + item);
            produceCondition.signalAll();
            return item;
        } finally {
            locker.unlock();
        }
    }
}
