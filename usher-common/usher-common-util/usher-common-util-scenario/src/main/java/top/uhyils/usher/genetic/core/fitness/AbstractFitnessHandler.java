package top.uhyils.usher.genetic.core.fitness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.genetic.core.individual.Individual;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.Pair;

/**
 * 适应度函数模板
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月25日 14时35分
 */
public abstract class AbstractFitnessHandler<T, E> implements FitnessHandler<T, E> {


    @Override
    public Double fitness(Individual<T, E> individual) {
        T[] randomParam = testParams();
        return fitness(individual, randomParam);
    }

    @Override
    public List<Individual<T, E>> findTopPercentage(List<Individual<T, E>> individuals, Double percentage, Integer min) {
        int targetSize = (int) (individuals.size() * percentage);
        // 选给定的最小值和实际百分比的大小中较大的
        int realTargetSize = Math.max(targetSize, min);
        // 再选计算出来的数量和实际大小中较小的那个
        realTargetSize = Math.min(realTargetSize, individuals.size());

        T[] randomParam = testAllParams();
        Pair<Individual<T, E>, Double>[] array = individuals.stream().map(t -> new Pair<>(t, fitness(t, randomParam))).sorted(Comparator.comparingDouble(t -> t.getValue())).toArray(Pair[]::new);
        individuals = Arrays.stream(array).map(Pair::getKey).collect(Collectors.toList());
        List<Individual<T, E>> result = new ArrayList<>(realTargetSize);
        for (int i = 0; i < realTargetSize; i++) {
            result.add(individuals.get(i));
        }
        return result;
    }

    @Override
    public Individual<T, E> findBestPercentage(List<Individual<T, E>> individuals) {
        T[] randomParam = testAllParams();
        Pair<Individual<T, E>, Double>[] array = individuals.stream().map(t -> new Pair<>(t, fitness(t, randomParam))).sorted(Comparator.comparingDouble(t -> t.getValue())).toArray(Pair[]::new);
        individuals = Arrays.stream(array).map(Pair::getKey).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(individuals)) {
            return null;
        }
        return individuals.get(0);
    }

    @Override
    public Double fitnessByMean(List<Individual<T, E>> topPercentage) {
        T[] params = testAllParams();
        List<Double> allResult = new ArrayList<>(topPercentage.size());
        for (Individual<T, E> teIndividual : topPercentage) {
            allResult.add(fitness(teIndividual, params));
        }
        return allResult.stream().mapToDouble(t -> t).average().getAsDouble();
    }


    @Override
    public Double fitness(Individual<T, E> individual, T param) {
        // 计算模型结果
        E calculationResult = individual.findResult(param);
        // 把模型结果和入参放入已知的函数中计算,得到结果并计算最终适应度
        return forwardAndLoss(calculationResult, param);
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
    public Double fitness(Individual<T, E> individual, T[] params) {
        double sum = 0;
        for (T param : params) {
            Double fitness = fitness(individual, param);
            sum += fitness;
        }
        return sum / params.length;
    }


    /**
     * 正向计算 + 损失函数计算
     *
     * @param calculationResult 模型计算的结果
     * @param param             入参
     *
     * @return 差距量化后的值
     */
    protected abstract Double forwardAndLoss(E calculationResult, T param);

    /**
     * 获取测试集
     *
     * @return 生产的入参(测试集)
     */
    @NotNull
    protected abstract T[] testParams();

    /**
     * 获取测试集
     *
     * @return 生产的入参(测试集)
     */
    @NotNull
    protected abstract T[] testAllParams();
}
