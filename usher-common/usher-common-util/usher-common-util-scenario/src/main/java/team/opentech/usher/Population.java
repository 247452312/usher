package team.opentech.usher;

import java.io.Serializable;
import java.util.List;

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
    void iteration();

    /**
     * 迭代
     *
     * @param size 迭代次数
     */
    void iteration(int size);

    /**
     * 获取结果
     *
     * @param param
     *
     * @return
     */
    E findResult(T param);

    /**
     * 获取所有的个体
     */
    List<Individual<T, E>> allIndividuals();

    /**
     * 初始化种群
     */
    void init();
}
