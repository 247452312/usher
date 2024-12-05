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
        boolean success = doSend(request);
        if (!success) {
            throw new RuntimeException("发送消息失败");
        }
        Serializable unique = findUniqueFromRequest(request);
        // 请求第一次
        if (rpcResponseMap.containsKey(unique)) {
            R rpcData = rpcResponseMap.get(unique);
            rpcResponseMap.remove(unique);
            return rpcData;
        }
        // 等待第一次
        CountDownLatch value = new CountDownLatch(1);
        waitLock.put(unique, value);
        //阻塞
        value.await(50000L, TimeUnit.MILLISECONDS);

        //查询第二次是否存在
        if (rpcResponseMap.containsKey(unique)) {
            R rpcData = rpcResponseMap.get(unique);
            rpcResponseMap.remove(unique);
            return rpcData;
        }
        timeOutUnique.add(unique);
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
        awaken(unique);
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
    private void awaken(Serializable unique) {
        try {
            // 重复等待waitLock 直到等待线程将key置入
            while (!waitLock.containsKey(unique)) {
                // 如果responseMap中不存在结果,说明已经被拿走.可以直接结束线程
                if (!rpcResponseMap.containsKey(unique)) {
                    return;
                }
                Thread.sleep(100L);
            }
        } catch (InterruptedException e) {
        }
        CountDownLatch countDownLatch = waitLock.get(unique);
        waitLock.remove(unique);
        //唤醒
        countDownLatch.countDown();


    }

}
