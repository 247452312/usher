package top.uhyils.usher.genetic;

import top.uhyils.usher.enums.DimensionalityReductionTypeEnum;
import top.uhyils.usher.enums.StandardizationTypeEnum;

/**
 * 遗传算法训练过程
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 08时10分
 */
public interface InitGenetic extends Genetic {

    /**
     * 训练
     *
     * @param times 训练次数
     * @param log   是否需要日志
     *
     * @return 训练结果, 模型在测试集上的准确度, 结果值越接近0 表示准确度越高
     */
    double train(int times, Boolean log);

    /**
     * 训练
     *
     * @param times 训练次数
     * @param log   是否需要日志
     *
     * @return 训练结果, 模型在测试集上的准确度, 结果值越接近0 表示准确度越高
     */
    double trainAndLearnByTrainData(int times, Boolean log);

    /**
     * 设置数据降维参数
     *
     * @param type 降维操作类型
     */
    void setDimensionalityReductionConfig(DimensionalityReductionTypeEnum type);


    /**
     * 设置数据标准化参数
     *
     * @param type               标准化方式
     * @param resultStandardized 是否标准化结果集
     */
    void setExtractedConfig(StandardizationTypeEnum type, boolean resultStandardized);


}
