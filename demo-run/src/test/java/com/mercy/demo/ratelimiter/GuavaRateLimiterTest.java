package com.mercy.demo.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import com.mercy.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * 测试使用基于guava的令牌桶算法实现秒杀
 * </p>
 *
 * @author mercy created on 2019-2-11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class GuavaRateLimiterTest {

    /**
     * 有很多个任务，但希望每秒不超过X个，可用此类
     */
    @Test
    public void getRateLimiter() throws InterruptedException {
        //代表一秒最多多少个
        RateLimiter rateLimiter = RateLimiter.create(10);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.err.println("T1" + "获得执行权---" + i);

                System.err.println("T1等待时间:" + rateLimiter.acquire());
            }
        }, "T1");
        Thread t2 = new Thread(() -> {
            //循环中调用tryAcquire会有问题.第一次调用返回true  后续返回false
            for (int i = 0; i < 10; i++) {
                boolean tryAcquire = rateLimiter.tryAcquire();
                System.out.println(tryAcquire);
                if (tryAcquire) {

                    System.err.println("T2" + "获得执行权---" + i);

                    // System.err.println("T2等待时间:" + rateLimiter.acquire());
                }
            }
        }, "T2");
        // t1.start();
        t2.start();
        Thread.sleep(10000);
    }
}
