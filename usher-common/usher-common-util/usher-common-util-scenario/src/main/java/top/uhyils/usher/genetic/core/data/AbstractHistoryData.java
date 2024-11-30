package top.uhyils.usher.genetic.core.data;

import java.util.Map;
import top.uhyils.usher.genetic.core.dna.Dna;
import top.uhyils.usher.genetic.core.individual.Individual;
import top.uhyils.usher.util.Pair;

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
    public Pair<Integer, Integer> getCoeff(int index) {
        return null;
    }

    @Override
    public void setCoeff(int index, Integer power, Integer coeff) {

    }

    @Override
    public void directionalLearn(T param, E value, Double learningRate) {
        // 历史数据不会学习
    }

    @Override
    public Individual<T, E> cross(Individual<T, E> individual) {
        // 历史数据不需要交叉
        return null;
    }

    @Override
    public void variation(long virusDna) {
        // 历史数据不需要变异
    }


    @Override
    public Dna firstDna() {
        // 历史数据没有dna
        return null;
    }

    @Override
    public Dna secondDna() {
        // 历史数据没有dna
        return null;
    }

}
