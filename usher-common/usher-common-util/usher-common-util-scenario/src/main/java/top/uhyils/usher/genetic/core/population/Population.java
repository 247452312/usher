package top.uhyils.usher.genetic.core.population;

import java.io.Serializable;
import java.util.List;
import top.uhyils.usher.genetic.core.fitness.FitnessHandler;
import top.uhyils.usher.genetic.core.individual.Individual;
import top.uhyils.usher.util.Pair;

/**
 * 种群
 *
 * @param <T> 种群入参类型
 * @param <E> 种群计算结果类型
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月15日 15时52分
 */
public interface Population<T, E> extends Serializable {

    /**
     * 迭代
     */
    void iteration(FitnessHandler<T, E> fitnessHandler);

    /**
     * 迭代
     *
     * @param size 迭代次数
     */
    void iteration(FitnessHandler<T, E> fitnessHandler, int size);

    /**
     * 迭代
     */
    void iteration(FitnessHandler<T, E> fitnessHandler, List<Pair<T, E>> learnParam);

    /**
     * 迭代
     *
     * @param size 迭代次数
     */
    void iteration(FitnessHandler<T, E> fitnessHandler, int size, List<Pair<T, E>> learnParam);

    /**
     * 获取结果
     *
     * @param fitnessHandler 适应度函数, 用来获取较好的个体
     * @param param
     *
     * @return
     */
    E findResult(FitnessHandler<T, E> fitnessHandler, T param);

    /**
     * 获取所有的个体
     */
    List<Individual<T, E>> allIndividuals();

    /**
     * 初始化种群
     */
    Population<T, E> init();
}
