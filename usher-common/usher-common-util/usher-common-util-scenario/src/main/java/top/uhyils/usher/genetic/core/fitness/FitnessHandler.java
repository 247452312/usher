package top.uhyils.usher.genetic.core.fitness;

import java.util.List;
import top.uhyils.usher.genetic.core.individual.Individual;

/**
 * 适应度函数
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月15日 16时02分
 */
public interface FitnessHandler<T, E> {

    /**
     * 计算适应度 适应度越趋近1则适应度越高
     *
     * @param individual 种群中的个体
     *
     * @return 适应度 范围为 0-1
     */
    Double fitness(Individual<T, E> individual);

    /**
     * 计算适应度 适应度越趋近0则适应度越高
     *
     * @param individual 种群中的个体
     *
     * @return 适应度
     */
    Double fitness(Individual<T, E> individual, T x);

    /**
     * 计算适应度 适应度越趋近1则适应度越高
     *
     * @param individual 种群中的个体
     *
     * @return 适应度 范围为 0-1
     */
    Double fitness(Individual<T, E> individual, T[] x);


    /**
     * 获取适应度为前一定百分比的个体
     *
     * @param individuals 一堆个体
     * @param percentage  要获取的比例 如果要获取前10% 则传入0.1
     * @param min         最少获取个数
     */
    List<Individual<T, E>> findTopPercentage(List<Individual<T, E>> individuals, Double percentage, Integer min);

    /**
     * 获取适应度为第一的
     *
     * @param individuals 一堆个体
     */
    Individual<T, E> findBestPercentage(List<Individual<T, E>> individuals);

    /**
     * 计算多个个体平均适应度
     *
     * @param topPercentage 个体
     *
     * @return
     */
    Double fitnessByMean(List<Individual<T, E>> topPercentage);

    /**
     * 计算结果
     *
     * @param params 入参
     *
     * @return
     */
    E[] calculateResult(T[] params);

    /**
     * 计算结果
     *
     * @param param 入参
     *
     * @return
     */
    E calculateResult(T param);
}
