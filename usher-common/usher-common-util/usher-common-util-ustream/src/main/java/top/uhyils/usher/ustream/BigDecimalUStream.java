package top.uhyils.usher.ustream;

import java.math.BigDecimal;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月27日 13时20分
 */
public interface BigDecimalUStream extends UStream<BigDecimal> {

    /**
     * 最大
     *
     * @return
     */
    default BigDecimal max() {
        return max(BigDecimal::compareTo);
    }

    /**
     * 最小
     *
     * @return
     */
    default BigDecimal min() {
        return min(BigDecimal::compareTo);
    }

    /**
     * 均值
     *
     * @return
     */
    default BigDecimal avg() {
        int count = count();
        BigDecimal sum = sum();
        return count == 0 ? null : sum.divide(new BigDecimal(count));
    }

    /**
     * 求和
     *
     * @return
     */
    default BigDecimal sum() {
        UObject<BigDecimal> bigDecimalUObject = new UObject<>(new BigDecimal(0));
        consume(t -> bigDecimalUObject.set(bigDecimalUObject.get().add(t)));
        return bigDecimalUObject.get();
    }
}
