package team.opentech.usher.core.data;

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
        for (int i = 0; i < sortT.length - 1; i++) {
            if (compare(sortT[i], param) < 0 && compare(sortT[i + 1], param) > 0) {
                return meanResult(sortT[i], testData.get(sortT[i]), sortT[i + 1], testData.get(sortT[i + 1]), param);
            }
        }
        return testData.get(sortT[sortT.length - 1]);
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