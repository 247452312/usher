package team.opentech.usher;

import java.util.List;

/**
 * 适应度函数
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月15日 16时02分
 */
public interface FitnessHandler<T, E> {

    /**
     * 计算适应度
     *
     * @param individual 种群中的个体
     *
     * @return 适应度 范围为 0-1
     */
    Float fitness(Individual<T, E> individual);

    /**
     * 计算适应度
     *
     * @param individual 种群中的个体
     *
     * @return 适应度 范围为 0-1
     */
    Float fitness(Individual<T, E> individual, float x);

    /**
     * 计算适应度
     *
     * @param individual 种群中的个体
     *
     * @return 适应度 范围为 0-1
     */
    Float fitness(Individual<T, E> individual, float[] x);

    /**
     * 获取适应度为前一定百分比的个体
     *
     * @param individuals 一堆个体
     * @param percentage  要获取的比例 如果要获取前10% 则传入0.1
     * @param min         最少获取个数
     */
    List<Individual<T, E>> findTopPercentage(List<Individual<T, E>> individuals, Float percentage, Integer min);
}
