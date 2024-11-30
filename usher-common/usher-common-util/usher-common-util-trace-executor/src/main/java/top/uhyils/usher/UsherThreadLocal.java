package top.uhyils.usher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月27日 15时01分
 */
public class UsherThreadLocal<T> extends InheritableThreadLocal<T> {

    private static Map<Thread, Map<UsherThreadLocal<?>, Object>> cacheMyThreadLocal = new HashMap<>();

    public UsherThreadLocal() {
    }

    public static Map<UsherThreadLocal<?>, Object> copy() {
        Thread t = Thread.currentThread();
        return cacheMyThreadLocal.get(t);
    }

    /**
     * 初始化这个线程run之前的搜歌threadLocal
     *
     * @param cache
     */
    public static void initChildThreadLocal(Map<UsherThreadLocal<?>, Object> cache) {
        cacheMyThreadLocal.put(Thread.currentThread(), cache);
    }

    /**
     * 删除线程相关的所有threadLocal
     */
    public static void removeChildThreadLocal() {
        cacheMyThreadLocal.remove(Thread.currentThread());
    }


    @Override
    public T get() {
        Thread thread = Thread.currentThread();
        Optional<Map<UsherThreadLocal<?>, Object>> usherThreadLocalObjectMap = Optional.ofNullable(cacheMyThreadLocal.get(thread));
        return usherThreadLocalObjectMap.map(t -> (T) t.get(this)).orElse(super.get());
    }

    @Override
    public void set(T value) {
        Thread thread = Thread.currentThread();
        Map<UsherThreadLocal<?>, Object> usherThreadLocalObjectMap = cacheMyThreadLocal.computeIfAbsent(thread, k -> new HashMap<>());
        usherThreadLocalObjectMap.put(this, value);
        super.set(value);
    }

    @Override
    public void remove() {
        Thread thread = Thread.currentThread();
        Map<UsherThreadLocal<?>, Object> usherThreadLocalObjectMap = cacheMyThreadLocal.get(thread);
        if (usherThreadLocalObjectMap == null) {
            return;
        }
        usherThreadLocalObjectMap.remove(this);
    }
}
