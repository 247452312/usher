package team.opentech.usher.thread;

import team.opentech.usher.MyExecutorWrapper;
import team.opentech.usher.util.SpringUtil;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用于获取自定义线程池
 *
 * @author 来自网络
 */
public class ThreadPoolExecutorUtil {

    public static ThreadPoolExecutor getPoll() {
        AsyncTaskProperties properties = SpringUtil.getBean(AsyncTaskProperties.class);
        return MyExecutorWrapper.createByThreadPoolExecutor(new ThreadPoolExecutor(
            properties.getCorePoolSize(),
            properties.getMaxPoolSize(),
            properties.getKeepAliveSeconds(),
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(properties.getQueueCapacity()),
            new TheadFactoryName()
        ));
    }
}
