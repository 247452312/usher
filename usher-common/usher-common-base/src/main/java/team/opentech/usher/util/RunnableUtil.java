package team.opentech.usher.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月07日 11时49分
 */
public final class RunnableUtil {

    /**
     * 最佳线程数目 = （（线程等待时间+线程CPU时间）/线程CPU时间 ）* CPU数目
     * 异步用的线程池
     */
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() * 2, 60 * 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200));

    private static final ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private RunnableUtil() {
        throw new RuntimeException("util不可实例化");
    }

    public static AsyncFuture<?> run(Runnable runnable) {
        long unique = SpringUtil.getBean(IdUtil.class).newId();
        AsyncRunnable asyncRunnable = new AsyncRunnable(runnable, unique);
        Future<?> submit = EXECUTOR.submit(asyncRunnable);
        return new AsyncFuture<>(submit, asyncRunnable.getUnique());
    }


    public static AsyncFuture<?> runDelay(Runnable runnable, long seconds) {
        long unique = SpringUtil.getBean(IdUtil.class).newId();
        AsyncRunnable asyncRunnable = new AsyncRunnable(runnable, unique);
        ScheduledFuture<?> schedule = scheduled.schedule(asyncRunnable, seconds, TimeUnit.SECONDS);
        return new AsyncFuture<>(schedule, asyncRunnable.getUnique());
    }

    public static <T> AsyncFuture<T> run(Callable<T> callable) {
        long unique = SpringUtil.getBean(IdUtil.class).newId();
        AsyncCallable<T> asyncRunnable = new AsyncCallable<>(callable, unique);
        Future<T> submit = EXECUTOR.submit(asyncRunnable);
        return new AsyncFuture<>(submit, asyncRunnable.getUnique());
    }


    /**
     * 异步执行某些东西
     *
     * @param runnable  要执行的东西
     * @param loopCount 循环次数
     * @param second    秒
     */
    public static long run(Runnable runnable, Integer loopCount, Long second) {
        long unique = SpringUtil.getBean(IdUtil.class).newId();
        if (loopCount == 1) {
            AsyncRunnable asyncRunnable = new AsyncRunnable(runnable, unique);
            EXECUTOR.submit(asyncRunnable);
        }
        CompletableFuture.runAsync(() -> {
            try {

                AsyncRunnable asyncRunnable = new AsyncRunnable(runnable, unique);
                Future<?> submit = EXECUTOR.submit(asyncRunnable);
                submit.get();
                for (int i = 1; i < loopCount; i++) {
                    ScheduledFuture<?> schedule = scheduled.schedule(asyncRunnable, second, TimeUnit.SECONDS);
                    schedule.get();
                }
            } catch (InterruptedException | ExecutionException e) {
                LogUtil.error(e);
            }
        });
        return unique;
    }


    /**
     * 异步执行某些东西
     *
     * @param runnable  要执行的东西
     * @param loopCount 循环次数
     * @param second    秒
     */
    public static <T> long run(Callable<T> runnable, Integer loopCount, Long second) {
        long unique = SpringUtil.getBean(IdUtil.class).newId();
        // 只发送一次的情况下,不需要异步
        if (loopCount == 1) {
            AsyncCallable<T> asyncRunnable = new AsyncCallable<>(runnable, unique);
            EXECUTOR.submit(asyncRunnable);
        }
        CompletableFuture.runAsync(() -> {
            try {
                AsyncCallable<T> asyncRunnable = new AsyncCallable<>(runnable, unique);
                Future<?> submit = EXECUTOR.submit(asyncRunnable);
                submit.get();
                for (int i = 1; i < loopCount; i++) {
                    ScheduledFuture<?> schedule = scheduled.schedule(asyncRunnable, second, TimeUnit.SECONDS);
                    schedule.get();
                }
            } catch (InterruptedException | ExecutionException e) {
                LogUtil.error(e);
            }
        });
        return unique;
    }

    public static class AsyncFuture<T> {

        private Future<T> submit;

        private Long unique;

        public AsyncFuture(Future<T> submit, Long unique) {
            this.submit = submit;
            this.unique = unique;
        }

        public T get() throws ExecutionException, InterruptedException {
            return submit.get();
        }

        public Long getUnique() {
            return unique;
        }
    }

    public static class AsyncRunnable implements Runnable {

        private final Runnable runnable;

        private final Long unique;

        public AsyncRunnable(Runnable runnable, Long unique) {
            this.runnable = runnable;
            this.unique = unique;
        }

        @Override
        public void run() {
            runnable.run();
        }

        public Long getUnique() {
            return unique;
        }
    }

    public static class AsyncCallable<T> implements Callable<T> {

        private final Callable<T> callable;

        private final Long unique;

        public AsyncCallable(Callable<T> callable, Long unique) {
            this.callable = callable;
            this.unique = unique;
        }


        public Long getUnique() {
            return unique;
        }

        @Override
        public T call() throws Exception {
            return callable.call();
        }
    }
}
