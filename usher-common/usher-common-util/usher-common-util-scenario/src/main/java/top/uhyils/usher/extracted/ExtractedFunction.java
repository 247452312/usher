package top.uhyils.usher.extracted;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 09时08分
 */
public interface ExtractedFunction {

    /**
     * 数据标准化
     *
     * @param data
     * @param needDimensionIndex
     *
     * @return
     */
    double[][] extracted(double[][] data, int[] needDimensionIndex);

    /**
     * 数据标准化
     *
     * @param data
     * @param needDimensionIndex
     *
     * @return
     */
    double[] extracted(double[] data);
}
