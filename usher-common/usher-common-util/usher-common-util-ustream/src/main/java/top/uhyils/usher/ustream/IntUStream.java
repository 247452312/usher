package top.uhyils.usher.ustream;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月27日 08时47分
 */
public interface IntUStream extends UStream<Integer> {


    /**
     * 最大
     *
     * @return
     */
    default Integer max() {
        return max(Integer::compareTo);
    }

    /**
     * 最小
     *
     * @return
     */
    default Integer min() {
        return min(Integer::compareTo);
    }

    /**
     * 均值
     *
     * @return
     */
    default Double avg() {
        int count = count();
        int sum = sum();
        return count == 0 ? null : ((double) sum) / count;
    }

    /**
     * 求和
     *
     * @return
     */
    default int sum() {
        AtomicInteger integer = new AtomicInteger(0);
        consume(integer::addAndGet);
        return integer.get();
    }

}
