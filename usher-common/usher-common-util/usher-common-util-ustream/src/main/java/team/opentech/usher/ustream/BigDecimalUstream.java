package team.opentech.usher.ustream;

import java.math.BigDecimal;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月27日 13时20分
 */
public interface BigDecimalUstream extends Ustream<BigDecimal> {

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
        Uobject<BigDecimal> bigDecimalUobject = new Uobject<>(new BigDecimal(0));
        consume(t -> bigDecimalUobject.set(bigDecimalUobject.get().add(t)));
        return bigDecimalUobject.get();
    }
}
