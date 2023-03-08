package xyz.xcye.file.utils;

import xyz.xcye.core.util.threadpoll.ExecutorUtils;
import xyz.xcye.file.dto.FileEntityDTO;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qsyyke
 * @date Created in 2022/8/10 15:42
 */


public class UploadFileExecutor {
    private static final int QUEUE_CAPACITY = 100;
    private final static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            ExecutorUtils.getCorePollSize(1),
            ExecutorUtils.getCorePollSize(1),
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(QUEUE_CAPACITY));
    private static final ExecutorCompletionService<FileEntityDTO> completionService =
            new ExecutorCompletionService<>(executor, new LinkedBlockingQueue<>(QUEUE_CAPACITY));

    public static ThreadPoolExecutor getInstance() {
        return executor;
    }

}
