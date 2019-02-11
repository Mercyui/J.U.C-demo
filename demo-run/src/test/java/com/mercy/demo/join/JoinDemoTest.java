package com.mercy.demo.join;

import com.mercy.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class JoinDemoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JoinDemoTest.class);

    /**
     * <p>
     * 现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行？
     * </p>
     */
    @Test
    public void joinTest() throws Exception {

        Thread t1 = getThread("T1");

        Thread t2 = getThread("T2");

        Thread t3 = getThread("T3");

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        t3.join();

        LOGGER.info("[---{}]线程的优先级priority:---[{}]", Thread.currentThread().getName(), Thread.currentThread().getPriority());
    }

    private Thread getThread(String name) {
        return new Thread(() -> {
            LOGGER.info("logger 当前线程的name:[---{}]", Thread.currentThread().getName());
        }, name);
    }

}
