package top.uhyils.usher.genetic;

import top.uhyils.usher.genetic.model.GeneticModel;

/**
 * 遗传算法训练过程
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 08时10分
 */
public interface Genetic {


    /**
     * 测试
     *
     * @param param 参数
     *
     * @return 预测结果
     */
    Double test(Double[] param);

    /**
     * 导出模型
     *
     * @return 模型
     */
    GeneticModel exportModel();

}
