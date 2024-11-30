package top.uhyils.usher.ustream.util;

import top.uhyils.usher.ustream.UStream;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月26日 16时30分
 */
public final class UstreamUtil {

    private UstreamUtil() {
        throw new UnsupportedOperationException("不能实例化");
    }

    /**
     * 将一个元素转换成一个流 只不过只有一个元素的流
     *
     * @param t
     * @param <T>
     *
     * @return
     */
    public static <T> UStream<T> unit(T t) {
        return c -> c.accept(t);
    }
}
