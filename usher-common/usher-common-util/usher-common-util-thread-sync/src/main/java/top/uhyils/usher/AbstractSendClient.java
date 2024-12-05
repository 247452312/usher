package top.uhyils.usher;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月05日 09时40分
 */
public abstract class AbstractSendClient<T, R> implements SendClient<T, R> {


    /**
     * 存储返回值->如果返回值来了 但是还没有wait,就把返回值存在这里
     */
    protected Map<Serializable, R> rpcResponseMap = new ConcurrentHashMap<>();

    /**
     * 记录等待中的请求 -> 想获取的时候 还没有返回
     */
    protected volatile Map<Serializable, CountDownLatch> waitLock = new ConcurrentHashMap<>();

    /**
     * 超时的记录在这里,防止返回值的内存溢出
     */
    protected FixedLengthQueue<Serializable> timeOutUnique = new FixedLengthQueue<>(200, Serializable.class);

    @Override
    public R send(T request) throws InterruptedException, TimeoutException {
        // 优先创建CountDownLatch
        Serializable unique = findUniqueFromRequest(request);
        CountDownLatch value = new CountDownLatch(1);
        waitLock.put(unique, value);
        boolean success = doSend(request);
        if (!success) {
            waitLock.remove(unique);
            throw new RuntimeException("发送消息失败");
        }
        //阻塞
        value.await(50000L, TimeUnit.MILLISECONDS);

        waitLock.remove(unique);
        if (rpcResponseMap.containsKey(unique)) {
            R rpcData = rpcResponseMap.get(unique);
            rpcResponseMap.remove(unique);
            return rpcData;
        }
        timeOutUnique.add(unique);
        waitLock.remove(unique);
        throw new TimeoutException("client超出最大等待时间");
    }

    /**
     * 消息来时调用
     *
     * @param response
     */
    public void onResponse(R response) {
        Serializable unique = findUniqueFromResponse(response);
        // 先判断是否曾经执行过并且超时了
        Boolean contain = timeOutUnique.contain(unique);
        if (contain) {
            return;
        }
        rpcResponseMap.put(unique, response);
        // 尝试唤醒
        if (!awaken(unique)) {
            // 唤醒失败, 说明超时了 刚好并发没有判断到超时记录中存在当前返回值,那就移除返回值
            rpcResponseMap.remove(unique);
        }
    }

    /**
     * 从请求中获取唯一标示
     */
    protected abstract Serializable findUniqueFromRequest(T request);

    /**
     * 从返回值中获取唯一标示
     *
     * @param response
     *
     * @return
     */
    protected abstract Serializable findUniqueFromResponse(R response);

    /**
     * 发送请求
     *
     * @param request
     *
     * @return
     */
    protected abstract boolean doSend(T request);

    /**
     * 尝试唤醒
     *
     * @param unique
     */
    private boolean awaken(Serializable unique) {
        CountDownLatch countDownLatch = waitLock.get(unique);
        if (countDownLatch == null) {
            return false;
        }
        //唤醒
        countDownLatch.countDown();
        return true;


    }

}
