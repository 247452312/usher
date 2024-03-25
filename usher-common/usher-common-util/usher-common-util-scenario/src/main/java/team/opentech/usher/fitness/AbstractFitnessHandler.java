package team.opentech.usher.fitness;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import team.opentech.usher.FitnessHandler;
import team.opentech.usher.Individual;
import team.opentech.usher.annotation.NotNull;

/**
 * 适应度函数模板
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月25日 14时35分
 */
public abstract class AbstractFitnessHandler<T, E> implements FitnessHandler<T, E> {


    @Override
    public Float fitness(Individual<T, E> individual) {
        T[] randomParam = makeTestParams();
        return fitness(individual, randomParam);
    }

    @Override
    public List<Individual<T, E>> findTopPercentage(List<Individual<T, E>> individuals, Float percentage, Integer min) {

        int targetSize = (int) (individuals.size() * percentage);
        // 选给定的最小值和实际百分比的大小中较大的
        int realTargetSize = Math.max(targetSize, min);
        // 再选计算出来的数量和实际大小中较小的那个
        realTargetSize = Math.min(realTargetSize, individuals.size());

        T[] randomParam = makeTestParams();
        individuals.sort(Comparator.comparingDouble(value -> -fitness(value, randomParam)));
        List<Individual<T, E>> result = new ArrayList<>(realTargetSize);
        for (int i = 0; i < realTargetSize; i++) {
            result.add(individuals.get(i));
        }
        return result;
    }

    @Override
    public Float fitnessByMean(List<Individual<T, E>> topPercentage, T[] params) {
        return (float) topPercentage.stream().mapToDouble(t -> fitness(t, params)).average().getAsDouble();
    }


    @Override
    public Float fitness(Individual<T, E> individual, T param) {
        // 计算模型结果
        E calculationResult = individual.findResult(param);
        // 把模型结果和入参放入已知的函数中计算,得到结果并计算最终适应度
        return quantifyGap(calculationResult, param);
    }

    /**
     * 计算适应度,这里默认的是求均值
     *
     * @param individual 种群中的个体
     * @param params
     *
     * @return
     */
    @Override
    public Float fitness(Individual<T, E> individual, T[] params) {
        float sum = 0;
        for (T param : params) {
            sum += fitness(individual, param);
        }
        return sum / params.length;
    }


    /**
     * 量化差距
     *
     * @param calculationResult 模型计算的结果
     * @param param             入参
     *
     * @return 差距量化后的值
     */
    protected abstract Float quantifyGap(E calculationResult, T param);

    /**
     * 生成测试用入参
     *
     * @return 生产的入参(测试集)
     */
    @NotNull
    protected abstract T[] makeTestParams();
}
