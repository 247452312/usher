package team.opentech.usher;

import java.io.Serializable;
import java.util.BitSet;

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
     * 变异
     */
    void variation(byte[] virusDna);

    /**
     * 获取结果
     */
    E findResult(T param);


    /**
     * 第一条DNA
     */
    BitSet firstDna();

    /**
     * 第二条DNA
     */
    BitSet secondDna();

    /**
     * dna长度
     *
     * @return
     */
    int size();

}
