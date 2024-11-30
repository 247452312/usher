package top.uhyils.usher.genetic.core.data;

import java.util.Arrays;
import java.util.Map;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月25日 16时34分
 */
public abstract class AbstractHistoryMeanData<T, E> extends AbstractHistoryData<T, E> {

    private T[] sortT;

    protected AbstractHistoryMeanData(Map<T, E> testData, T[] zeroParamArray) {
        super(testData);
        this.sortT = sortTestDataParam(testData, zeroParamArray);
    }


    @Override
    public E findResult(T param) {
        if (testData.containsKey(param)) {
            return testData.get(param);
        }
        // 二分法查找
        int left = 0;
        int right = sortT.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (compare(sortT[mid + 1], param) < 0) {
                left = mid + 1;
            } else if (compare(sortT[mid], param) > 0) {
                right = mid - 1;
            } else {
                return meanResult(sortT[mid], testData.get(sortT[mid]), sortT[mid + 1], testData.get(sortT[mid + 1]), param);
            }

        }
        return testData.get(sortT[sortT.length - 1]);
    }

    @Override
    public int size() {
        // 历史数据评价类 不需要size
        return 0;
    }

    /**
     * 求两个值的均值
     *
     * @param param1  入参一
     * @param result1 结果一
     * @param param2  入参二
     * @param result2 结果二
     * @param param   实际入参
     *
     * @return
     */
    protected abstract E meanResult(T param1, E result1, T param2, E result2, T param);

    /**
     * param 排序
     */
    protected abstract int compare(T o1, T o2);

    /**
     * 将根据key排序
     *
     * @param testData
     *
     * @return
     */
    private T[] sortTestDataParam(Map<T, E> testData, T[] zeroParamArray) {
        T[] array = testData.keySet().toArray(zeroParamArray);
        Arrays.sort(array, this::compare);
        return array;
    }
}
