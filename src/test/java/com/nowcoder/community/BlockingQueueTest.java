package com.nowcoder.community;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author:xiaoyang
 * @Title: BlockingQueueTest
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/10/02 15:03
 */

public class BlockingQueueTest {
    public static void main(String[] args) {
        //阻塞队列，队列满了，生产者线程阻塞，队列空了，消费者线程阻塞
        BlockingQueue queue = new ArrayBlockingQueue(10);
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }
}

class Producer implements Runnable {
    private BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                //每隔20毫秒生产一个数据
                Thread.sleep(20);
                queue.put(i);
                System.out.println(Thread.currentThread().getName() + "生产：" + queue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class Consumer implements Runnable {

    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
            try {
                while (true) {
                    Thread.sleep(new Random().nextInt(1000));
                    queue.take();
                    System.out.println(Thread.currentThread() + "消费：" + queue.size());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}