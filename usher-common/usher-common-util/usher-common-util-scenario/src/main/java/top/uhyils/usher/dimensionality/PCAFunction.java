package top.uhyils.usher.dimensionality;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import top.uhyils.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 08时53分
 */
public class PCAFunction implements DimensionalityReductionFunction {

    private static final double DEFAULT_RATE = 0.1;

    @Override
    public double[][] createDimensionalityReductionMatrix(double[][] data, double rate) {
        double[][] fileData = data;
        // 降维&正交化
        // 1.计算相关系数矩阵  这里使用香农信息论中的互信息理论
        // 互信息理论中 相关系数公式为 相关系数=2I(x,y)/(H(x),H(y))  其中 I(x,y) = H(x) + H(y) - H(x,y)
        // 里面 H(x) = E[log(1/P(x))] = ∑P(xi)log(1/P(xi))
        // H(x,y) = E[log(1/P(xy))] = ∑P(xi,yi)log(1/P(xi,yi))
        // 其中p(x) 代表概率 高维中某一维的概率计算方式应该遵循 将[最小值, 最大值] 平均分为100份, 落在同一份中的值属于同一区域的概率,计算方式也变成了计算当前区域的概率

        // 编程计算 获取各个维度的最大值最小值 并求 方块值 = 差值/100
        int dimensionLength = fileData[0].length;
        double[] max = new double[dimensionLength];
        double[] min = new double[dimensionLength];
        for (double[] fileDatum : fileData) {
            for (int i = 0; i < fileDatum.length; i++) {
                if (fileDatum[i] > max[i]) {
                    max[i] = fileDatum[i];
                }
                if (fileDatum[i] < min[i]) {
                    min[i] = fileDatum[i];
                }
            }
        }
        double[] standardLength = new double[dimensionLength];
        for (int i = 0; i < max.length; i++) {
            standardLength[i] = (max[i] - min[i]) / 100;
        }

        // 计算每个维度里每个标准长度的概率
        double[][] p = new double[dimensionLength][100];
        for (double[] fileDatum : fileData) {
            for (int i = 0; i < dimensionLength; i++) {
                if (fileDatum[i] == max[i]) {
                    p[i][99] += 1;
                    continue;
                }
                // 数据的第i个维度的数据落在了第index个标准长度中
                int index = (int) ((fileDatum[i] - min[i]) / standardLength[i]);
                p[i][index] += 1;
            }
        }
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                p[i][j] = p[i][j] / fileData.length;
            }
        }

        // 求每一个维度的信息熵
        double[] h = new double[dimensionLength];
        for (int i = 0; i < dimensionLength; i++) {
            // 第i个维度的每个标准长度概率
            double[] pi = p[i];
            for (double v : pi) {
                if (v != 0) {
                    h[i] += -v * Math.log(v);
                }
            }
        }

        // 求联合概率 这里的四个维度分别为 维度a 维度b a标准长度所在 b标准长度所在
        double[][][][] pxy = new double[dimensionLength][dimensionLength][100][100];
        for (double[] fileDatum : fileData) {
            for (int i = 0; i < dimensionLength; i++) {
                int iIndex;
                if (fileDatum[i] == max[i]) {
                    iIndex = 99;
                } else {
                    iIndex = (int) ((fileDatum[i] - min[i]) / standardLength[i]);
                }
                for (int j = 0; j < dimensionLength; j++) {
                    int jIndex;
                    if (fileDatum[j] == max[j]) {
                        jIndex = 99;
                    } else {
                        jIndex = (int) ((fileDatum[j] - min[j]) / standardLength[j]);
                    }
                    pxy[i][j][iIndex][jIndex] += 1;
                }
            }
        }
        for (double[][][] item1 : pxy) {
            for (double[][] item2 : item1) {
                for (double[] item3 : item2) {
                    for (int i = 0; i < item3.length; i++) {
                        item3[i] = item3[i] / fileData.length;
                    }
                }
            }
        }
        // 求联合信息熵
        double[][] hxy = new double[dimensionLength][dimensionLength];
        for (int i = 0; i < dimensionLength; i++) {
            for (int j = 0; j < dimensionLength; j++) {
                // 维度i 和维度j 的所有概率
                double[][] pij = pxy[i][j];
                for (double[] doubles : pij) {
                    for (double aDouble : doubles) {
                        if (aDouble != 0) {
                            hxy[i][j] += -aDouble * Math.log(aDouble);
                        }
                    }
                }
            }
        }

        // 求最终的香农互信息矩阵 可以替代相关系数矩阵
        double[][] mutualInformationCorrelationCoefficientMatrix = new double[dimensionLength][dimensionLength];
        for (int i = 0; i < dimensionLength; i++) {
            for (int j = 0; j < dimensionLength; j++) {
                double ixy = h[i] + h[j] - hxy[i][j];
                mutualInformationCorrelationCoefficientMatrix[i][j] = ixy * 2 / (h[i] + h[j]);
            }
        }

        RealMatrix matrix = MatrixUtils.createRealMatrix(mutualInformationCorrelationCoefficientMatrix);

        // 计算矩阵的特征值
        List<Pair<Double, double[]>> eigList = new ArrayList<>();
        EigenDecomposition ed = new EigenDecomposition(matrix);

        double[] realEigenvalues = ed.getRealEigenvalues();
        double sum = 0;
        for (int i = 0; i < realEigenvalues.length; i++) {
            // 特征值
            double realEigenvalue = realEigenvalues[i];
            // 特征向量
            RealVector eigenvector = ed.getEigenvector(i);
            sum += realEigenvalue;
            int dimension = eigenvector.getDimension();
            double[] doubles = new double[dimension];
            eigList.add(new Pair<>(realEigenvalue, doubles));
            for (int j = 0; j < dimension; j++) {
                doubles[j] = eigenvector.getEntry(j);
            }
        }
        // 过滤掉不足特征值平均值rate的特征
        sum /= dimensionLength;
        sum *= rate;
        double finalSum = sum;
        double[][] transArray = eigList.stream().filter(t -> t.getKey() > finalSum).map(Pair::getValue).toArray(double[][]::new);
        RealMatrix transMatrix = MatrixUtils.createRealMatrix(transArray);
        // 转置为转换矩阵
        transMatrix = transMatrix.transpose();

        return transMatrix.getData();
    }

    @Override
    public double[][] createDimensionalityReductionMatrix(double[][] data) {
        return createDimensionalityReductionMatrix(data, DEFAULT_RATE);
    }
}
