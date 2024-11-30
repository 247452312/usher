package top.uhyils.usher.extracted;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 09时09分
 */
public class ZScoreFunction implements ExtractedFunction {

    @Override
    public double[][] extracted(double[][] data, int[] needDimensionIndex) {
        double[] floats = data[0];
        if (needDimensionIndex == null) {
            needDimensionIndex = new int[floats.length];
            for (int i = 0; i < floats.length; i++) {
                needDimensionIndex[i] = i;
            }
        }
        // 求均值
        double[] avg = new double[floats.length];

        for (double[] fileDatum : data) {
            for (int dimension : needDimensionIndex) {
                avg[dimension] += fileDatum[dimension];
            }

        }

        for (int dimension : needDimensionIndex) {
            avg[dimension] = avg[dimension] / data.length;
        }

        // 求标准差
        double[] variance = new double[floats.length];
        for (double[] fileDatum : data) {
            for (int dimension : needDimensionIndex) {
                double v = fileDatum[dimension] - avg[dimension];
                variance[dimension] += v * v;
            }
        }
        for (int i = 0; i < variance.length; i++) {
            variance[i] = variance[i] / (data.length - 1);
            variance[i] = Math.sqrt(variance[i]);
        }

        // 标准化处理
        for (double[] item : data) {
            for (int dimension : needDimensionIndex) {
                item[dimension] = (item[dimension] - avg[dimension]) / variance[dimension];
            }
        }
        return data;

    }

    @Override
    public double[] extracted(double[] data) {
        // 求均值
        double avg = 0;

        for (double v : data) {
            avg += v;
        }

        avg = avg / data.length;

        // 求标准差
        double std = 0;
        for (double v : data) {
            std += v * v;
        }
        std = std / (data.length - 1);
        std = Math.sqrt(std);

        // 标准化处理
        for (int i = 0; i < data.length; i++) {
            data[i] = (data[i] - avg) / std;
        }
        return data;
    }
}
