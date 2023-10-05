package xyz.xcye.auth.threadpoll;

import xyz.xcye.core.util.threadpoll.ExecutorUtils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qsyyke
 * @date Created in 2022/8/10 08:36
 */


public class WriteLoginInfoExecutor {
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            ExecutorUtils.getCorePollSize(1),
            ExecutorUtils.getCorePollSize(5),
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());

    public static ThreadPoolExecutor getInstance() {
        return executor;
    }

}
