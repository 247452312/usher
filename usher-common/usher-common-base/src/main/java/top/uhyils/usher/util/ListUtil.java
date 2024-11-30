package top.uhyils.usher.util;

import java.util.Comparator;
import java.util.List;
import top.uhyils.usher.annotation.NotNull;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月20日 09时15分
 */
public final class ListUtil {

    private ListUtil() {
        throw new RuntimeException("util不能创建实例");
    }

    /**
     * 中位数
     *
     * @param results
     */
    public static <T extends Number> T median(@NotNull List<T> results) {
        Asserts.assertTrue(CollectionUtil.isNotEmpty(results), "取中位数");
        results.sort(Comparator.comparingDouble(Number::floatValue));
        return results.get(results.size() / 2);
    }

    public static <T extends Number> double mean(List<T> results) {
        double sum = 0F;
        for (T result : results) {
            sum += result.doubleValue();
        }
        return sum / results.size();
    }
}
