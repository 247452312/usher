package top.uhyils.usher.genetic;

/**
 * 通过模型初始化的遗传算法,可进行再训练
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 10时23分
 */
public interface UsedGenetic extends Genetic {

    /**
     * 数据标准化
     */
    double[][] extracted(double[][] data, int[] needDimensionIndex);

    /**
     * 数据标准化
     */
    double[] extracted(double[] data);


}
