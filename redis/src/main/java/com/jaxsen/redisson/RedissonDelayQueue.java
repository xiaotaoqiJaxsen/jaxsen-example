package com.jaxsen.redisson;

import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;

import java.util.concurrent.TimeUnit;

/**
 * 用 redisson 工具实现延迟队列
 * @author mayongjie
 * @date 2025/02/08 17:27
 **/
public class RedissonDelayQueue {

    public static final String BLOCK_DEQUE = "blockDeque";

    public static void main(String[] args) throws Exception {
        // 创建redisson客户端
        RedissonClient client = new RedissonClient("192.168.51.139:46379", null, "gowithmc");
        org.redisson.api.RedissonClient redissonClient = client.getRedissonClient();
        // 效果: 倒序输出
        // 向延迟队列中增加任务
        addTask(redissonClient);
        Thread.sleep(5000);
        // 获取延迟队列任务
        executeTask(redissonClient);
        redissonClient.shutdown();
    }

    private static void addTask(org.redisson.api.RedissonClient redissonClient) {
        // 创建关联阻塞队列,后续用户存放到期数据
        RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(BLOCK_DEQUE);
        // 延迟队列
        RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        // 向延迟队列中插入数据
        delayedQueue.offer(1, 10, TimeUnit.SECONDS);
        delayedQueue.offer(2, 9, TimeUnit.SECONDS);
        delayedQueue.offer(3, 8, TimeUnit.SECONDS);
        delayedQueue.offer(4, 7, TimeUnit.SECONDS);
        delayedQueue.offer(5, 6, TimeUnit.SECONDS);
        delayedQueue.offer(6, 5, TimeUnit.SECONDS);
        delayedQueue.offer(7, 4, TimeUnit.SECONDS);
        delayedQueue.offer(8, 3, TimeUnit.SECONDS);
        delayedQueue.offer(9, 2, TimeUnit.SECONDS);
    }

    private static void executeTask(org.redisson.api.RedissonClient redissonClient)throws Exception {
        RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(BLOCK_DEQUE);
        for (int i = 0; i < 9; i++) {
            Object take = blockingDeque.take();
            System.out.println("第" + (i + 1) + "次取数:" + take);
        }
        System.out.println("取数结束");
    }

}
