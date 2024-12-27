package top.uhyils.usher.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import top.uhyils.usher.UsherExecutorWrapper;
import top.uhyils.usher.util.SpringUtil;

/**
 * 用于获取自定义线程池
 *
 * @author 来自网络
 */
public class ThreadPoolExecutorUtil {

    public static ThreadPoolExecutor getPoll() {
        AsyncTaskProperties properties = SpringUtil.getBean(AsyncTaskProperties.class);
        return UsherExecutorWrapper.createByThreadPoolExecutor(new ThreadPoolExecutor(
            properties.getCorePoolSize(),
            properties.getMaxPoolSize(),
            properties.getKeepAliveSeconds(),
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(properties.getQueueCapacity()),
            new TheadFactoryName()
        ));
    }
}
