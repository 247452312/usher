package top.uhyils.usher;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * executor包装器
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月27日 15时03分
 */
public class UsherExecutorWrapper extends ThreadPoolExecutor {


    private final ExecutorService executor;

    private UsherExecutorWrapper(ThreadPoolExecutor executor) {
        super(executor.getCorePoolSize(), executor.getMaximumPoolSize(), executor.getKeepAliveTime(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS, executor.getQueue(), executor.getThreadFactory(), executor.getRejectedExecutionHandler());
        this.executor = executor;
    }

    /**
     * 创建一个新的executor
     *
     * @param executor
     *
     * @return
     */
    public static UsherExecutorWrapper createByThreadPoolExecutor(ThreadPoolExecutor executor) {
        return new UsherExecutorWrapper(executor);
    }

    @Override
    public void execute(Runnable command) {
        this.executor.execute(wrapperRunnable(command));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    /**
     * 包装
     *
     * @param command
     *
     * @return
     */
    private Runnable wrapperRunnable(Runnable command) {
        return this.new MyRunnableWrapper(command);
    }

    /**
     * runnable包装类
     */
    class MyRunnableWrapper implements Runnable {

        private final AtomicReference<Map<UsherThreadLocal<?>, Object>> tempSaveThreadLocal = new AtomicReference<>(UsherThreadLocal.copy());

        private final Runnable runnable;

        private MyRunnableWrapper(Runnable runnable) {
            this.runnable = runnable;
        }


        @Override
        public void run() {
            UsherThreadLocal.initChildThreadLocal(tempSaveThreadLocal.get());
            try {
                runnable.run();
            } finally {
                UsherThreadLocal.removeChildThreadLocal();
                tempSaveThreadLocal.set(null);
            }
        }
    }
}
