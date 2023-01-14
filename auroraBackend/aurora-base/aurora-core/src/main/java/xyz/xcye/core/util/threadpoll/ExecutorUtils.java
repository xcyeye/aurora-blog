package xyz.xcye.core.util.threadpoll;

/**
 * @author qsyyke
 * @date Created in 2022/8/10 08:40
 */


public class ExecutorUtils {

    public static int getCorePollSize(int additionalProcessorsNum) {
        return Runtime.getRuntime().availableProcessors() + additionalProcessorsNum;
    }
}
