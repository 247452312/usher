package team.opentech.usher.core.data;

import java.util.Map;
import team.opentech.usher.Individual;
import team.opentech.usher.lang.LongByte;

/**
 * 可以拟合历史数据的个体
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月25日 16时26分
 */
public abstract class AbstractHistoryData<T, E> implements Individual<T, E> {

    protected final Map<T, E> testData;

    protected AbstractHistoryData(Map<T, E> testData) {
        this.testData = testData;
    }


    @Override
    public Individual<T, E> cross(Individual<T, E> individual) {
        // 历史数据不需要交叉
        return null;
    }

    @Override
    public void variation(byte[] virusDna) {
        // 历史数据不需要变异
    }


    @Override
    public LongByte firstDna() {
        // 历史数据没有dna
        return null;
    }

    @Override
    public LongByte secondDna() {
        // 历史数据没有dna
        return null;
    }

}
