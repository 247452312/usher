package team.opentech.usher.core.heartDisease;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;
import team.opentech.usher.FitnessHandler;
import team.opentech.usher.Individual;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.util.CollectionUtil;
import team.opentech.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月26日 09时27分
 */
public class TestMain {


    @NotNull
    private static double[][] getFileData(String path) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        String fileData = dataReader.read(path);
        String[] person = fileData.split("\\n");

        double[][] result = new double[person.length][14];
        for (int i = 0; i < person.length; i++) {
            String s = person[i];
            String[] split = s.trim().split(" ");
            if (split.length < 58) {
                break;
            }
            result[i][0] = Float.valueOf(split[2].trim());
            result[i][1] = Float.valueOf(split[3].trim());
            result[i][2] = Float.valueOf(split[8].trim());
            result[i][3] = Float.valueOf(split[9].trim());
            result[i][4] = Float.valueOf(split[11].trim());
            result[i][5] = Float.valueOf(split[15].trim());
            result[i][6] = Float.valueOf(split[18].trim());
            result[i][7] = Float.valueOf(split[31].trim());
            result[i][8] = Float.valueOf(split[37].trim());
            result[i][9] = Float.valueOf(split[39].trim());
            result[i][10] = Float.valueOf(split[40].trim());
            result[i][11] = Float.valueOf(split[43].trim());
            result[i][12] = Float.valueOf(split[50].trim());
            result[i][13] = Float.valueOf(split[57].trim());
        }
        return result;
    }

    private static double[][] extracted(double[][] fileData) {
        double[] floats = fileData[0];
        // 求均值
        double[] avg = new double[floats.length];
        for (double[] fileDatum : fileData) {
            for (int i = 0; i < fileDatum.length; i++) {
                avg[i] += fileDatum[i];
            }
        }
        for (int i = 0; i < avg.length; i++) {
            avg[i] = avg[i] / fileData.length;
        }
        // 求方差
        double[] variance = new double[floats.length];
        for (double[] fileDatum : fileData) {
            for (int i = 0; i < fileDatum.length; i++) {
                double v = fileDatum[i] - avg[i];
                variance[i] += v * v;
            }
        }
        for (int i = 0; i < variance.length; i++) {
            variance[i] = variance[i] / (fileData.length - 1);
            variance[i] = Math.sqrt(variance[i]);
        }

        // 标准化处理
        for (double[] item : fileData) {
            for (int j = 0; j < item.length; j++) {
                item[j] = (item[j] - avg[j]) / variance[j];
            }
        }
        return fileData;

    }

    @Test
    void testMain() throws FileNotFoundException {
        double[][] fileData = getFileData("C:\\Users\\Lenovo\\Downloads\\heart+disease\\new.data");
        // 数据清洗
        fileData = clean(fileData);
        fileData = feature(fileData);
        Map<Double[], Double> transDataMap = new HashMap<>();
        for (double[] fileDatum : fileData) {
            Double[] array = Arrays.stream(fileDatum).boxed().toArray(Double[]::new);
            transDataMap.put(array, fileDatum[fileDatum.length - 1]);
        }
        Double[][] testData = transDataMap.keySet().toArray(new Double[0][]);

        FitnessHandler<Double[], Double> fitnessHandler = new TestHeartHistoryDataFunctionFitnessHandler(transDataMap);

        TestHeartFunctionPopulation testPopulation = new TestHeartFunctionPopulation(new Properties(), fitnessHandler);
        testPopulation.init();
        int lCount = 0;
        float minAbs = 0.0005F;
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMaximumFractionDigits(8);
        for (int i = 0; i < 1000; i++) {
            testPopulation.iteration();

            List<Individual<Double[], Double>> topPercentage = fitnessHandler.findTopPercentage(testPopulation.allIndividuals(), 0.1, 10);
            double result = 1 - fitnessHandler.fitnessByMean(topPercentage, testData);
            System.out.printf("第%d次迭代,   \t适应度为:%s", i, instance.format(result));
            System.out.println();
            if (minAbs > result) {
                lCount++;
            } else {
                lCount = 0;
            }
            if (lCount > 10) {
                break;
            }
        }
        int i = 1;


    }

    @Test
    void testEig() {
        double[][] a = new double[3][3];
        a[0][0] = 1;
        a[0][1] = 2;
        a[0][2] = 3;

        a[1][0] = 3;
        a[1][1] = 2;
        a[1][2] = 1;

        a[2][0] = 6;
        a[2][1] = 7;
        a[2][2] = 9;
        RealMatrix matrix = MatrixUtils.createRealMatrix(a);

        // 计算矩阵的特征值
        EigenDecomposition ed = new EigenDecomposition(matrix);

        // 打印特征值
        double[] realEigenvalues = ed.getRealEigenvalues();
        for (int i = 0; i < realEigenvalues.length; i++) {
            double realEigenvalue = realEigenvalues[i];
            RealVector eigenvector = ed.getEigenvector(i);
            System.out.println("第" + (i + 1) + "个特征值为: " + realEigenvalue + ", 对应特征向量为: " + eigenvector);
        }
        int i = 1;
    }

    /**
     * 特征工程
     *
     * @param fileData
     *
     * @return
     */
    private double[][] feature(double[][] fileData) {
        double[] floats = fileData[0];
        // 降维&正交化
        // 1.计算相关系数矩阵时这里使用香农信息论中的互信息理论
        // 互信息理论中 相关系数公式为 相关系数=2I(x,y)/(H(x),H(y))  其中 I(x,y) = H(x) + H(y) - H(x,y)
        // 里面 H(x) = E[log(1/P(x))] = ∑P(xi)log(1/P(xi))
        // H(x,y) = E[log(1/P(xy))] = ∑P(xi,yi)log(1/P(xi,yi))
        // 其中p(x) 代表概率 高维中某一维的概率计算方式应该遵循 将[最小值, 最大值] 平均分为100份, 落在同一份中的值属于同一区域的概率,计算方式也变成了计算当前区域的概率

        // 编程计算 获取各个维度的最大值最小值 并求 方块值 = 差值/100
        int dimensionLength = floats.length;
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
                for (int i1 = 0; i1 < pij.length; i1++) {
                    for (int j1 = 0; j1 < pij[i1].length; j1++) {
                        if (pij[i1][j1] != 0) {
                            hxy[i][j] += -pij[i1][j1] * Math.log(pij[i1][j1]);
                        }
                    }
                }
            }
        }

        // 求最终的香农互信息矩阵 可以替代相关系数矩阵
        double[][] result = new double[dimensionLength][dimensionLength];
        for (int i = 0; i < dimensionLength; i++) {
            for (int j = 0; j < dimensionLength; j++) {
                double ixy = h[i] + h[j] - hxy[i][j];
                result[i][j] = ixy * 2 / (h[i] + h[j]);
            }
        }

        RealMatrix matrix = MatrixUtils.createRealMatrix(result);

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
        sum /= 100;
        double finalSum = sum;
        double[][] array = eigList.stream().filter(t -> t.getKey() > finalSum).map(Pair::getValue).toArray(double[][]::new);
        RealMatrix transMatrix = MatrixUtils.createRealMatrix(array);
        transMatrix = transMatrix.transpose();

        // 特征组合
        return fileData;
    }

    @NotNull
    private double[][] clean(double[][] fileData) {
        if (CollectionUtil.isEmpty(fileData)) {
            return fileData;
        }
        // 过滤掉存在值为-9 的数据
        fileData = Arrays.stream(fileData).filter(this::filter).toArray(double[][]::new);
        // 数据标准化
        return extracted(fileData);
    }

    private boolean filter(double[] data) {
        for (double datum : data) {
            if (datum == -9) {
                return false;
            }
        }
        return true;
    }

}
