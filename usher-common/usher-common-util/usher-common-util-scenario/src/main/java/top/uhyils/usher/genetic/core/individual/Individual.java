package top.uhyils.usher.genetic.core.individual;

import java.io.Serializable;
import top.uhyils.usher.genetic.core.dna.Dna;
import top.uhyils.usher.util.Pair;

/**
 * 种群中的个体
 *
 * @param <T> 个体计算结果的入参类型
 * @param <E> 个体计算结果的结果类型
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月15日 15时53分
 */
public interface Individual<T, E> extends Serializable {

    /**
     * 交叉
     *
     * @param individual 交叉对象
     */
    Individual<T, E> cross(Individual<T, E> individual);

    /**
     * 获取系数
     *
     * @param index
     *
     * @return
     */
    Pair<Integer, Integer> getCoeff(int index);

    /**
     * 设置系数
     *
     * @param index
     */
    void setCoeff(int index, Integer power, Integer coeff);

    /**
     * 变异
     */
    void variation(long virusDna);

    /**
     * 获取结果
     */
    E findResult(T param);


    /**
     * 第一条DNA
     */
    Dna firstDna();

    /**
     * 第二条DNA
     */
    Dna secondDna();

    /**
     * dna长度
     *
     * @return
     */
    int size();


    /**
     * 混合遗传算法添加梯度下降学习思想, 此方法为反向学习
     *
     * @param param        入参
     * @param value        目标标签(正确结果)
     * @param learningRate 学习率
     */
    void directionalLearn(T param, E value, Double learningRate);
}
