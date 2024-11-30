package top.uhyils.usher.dimensionality;

/**
 * 降维方法
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 08时52分
 */
public interface DimensionalityReductionFunction {

    /**
     * 制作降维矩阵
     *
     * @param data 原始数据/训练数据
     * @param rate 降维阈值 0-1 此值越接近1 表示越少维度
     *
     * @return 对应的降维矩阵
     */
    double[][] createDimensionalityReductionMatrix(double[][] data, double rate);

    /**
     * 制作降维矩阵
     * 默认降维阈值为0.1
     *
     * @param data 原始数据/训练数据
     *
     * @return 对应的降维矩阵
     */
    double[][] createDimensionalityReductionMatrix(double[][] data);
}
