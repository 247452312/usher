package top.uhyils.usher.genetic.core.heartDisease;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.genetic.core.fitness.FitnessHandler;
import top.uhyils.usher.genetic.core.fitness.HistoryDataLogisticFitnessHandler;
import top.uhyils.usher.genetic.core.individual.Individual;
import top.uhyils.usher.genetic.core.population.Population;
import top.uhyils.usher.genetic.core.population.RandomDnaPopulation;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月26日 09时27分
 */
class TestMain {


    @NotNull
    private static Pair<double[][], double[]> getFileData(String path) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        String fileData = dataReader.read(path);
        List<String[]> collect = Arrays.stream(fileData.split("name")).map(t -> t.replace("\n", " ")).map(t -> t.trim().split(" ")).collect(Collectors.toList());

        double[][] datas = new double[collect.size()][13];
        double[] results = new double[collect.size()];
        for (int i = 0; i < collect.size(); i++) {
            String[] split = collect.get(i);
            if (split.length < 58) {
                break;
            }
            datas[i][0] = Double.parseDouble(split[2].trim());
            datas[i][1] = Double.parseDouble(split[3].trim());
            datas[i][2] = Double.parseDouble(split[8].trim());
            datas[i][3] = Double.parseDouble(split[9].trim());
            datas[i][4] = Double.parseDouble(split[11].trim());
            datas[i][5] = Double.parseDouble(split[15].trim());
            datas[i][6] = Double.parseDouble(split[18].trim());
            datas[i][7] = Double.parseDouble(split[31].trim());
            datas[i][8] = Double.parseDouble(split[37].trim());
            datas[i][9] = Double.parseDouble(split[39].trim());
            datas[i][10] = Double.parseDouble(split[40].trim());
            datas[i][11] = Double.parseDouble(split[43].trim());
            datas[i][12] = Double.parseDouble(split[50].trim());
            results[i] = Double.parseDouble(split[57].trim()) >= 1 ? 1 : 0;
        }

        return new Pair<>(datas, results);
    }

    private static double[][] extracted(double[][] fileData, int[] needDimensionLength) {
        double[] floats = fileData[0];
        if (needDimensionLength == null) {
            needDimensionLength = new int[floats.length];
            for (int i = 0; i < floats.length; i++) {
                needDimensionLength[i] = i;
            }
        }
        // 求均值
        double[] avg = new double[floats.length];

        for (double[] fileDatum : fileData) {
            for (int dimension : needDimensionLength) {
                avg[dimension] += fileDatum[dimension];
            }

        }

        for (int dimension : needDimensionLength) {
            avg[dimension] = avg[dimension] / fileData.length;
        }

        // 求标准差
        double[] variance = new double[floats.length];
        for (double[] fileDatum : fileData) {
            for (int dimension : needDimensionLength) {
                double v = fileDatum[dimension] - avg[dimension];
                variance[dimension] += v * v;
            }
        }
        for (int i = 0; i < variance.length; i++) {
            variance[i] = variance[i] / (fileData.length - 1);
            variance[i] = Math.sqrt(variance[i]);
        }

        // 标准化处理
        for (double[] item : fileData) {
            for (int dimension : needDimensionLength) {
                item[dimension] = (item[dimension] - avg[dimension]) / variance[dimension];
            }
        }
        return fileData;

    }

    private static double[] extracted(double[] result) {
        // 求均值
        double avg = 0;

        for (double v : result) {
            avg += v;
        }

        avg = avg / result.length;

        // 求标准差
        double std = 0;
        for (double v : result) {
            std += v * v;
        }
        std = std / (result.length - 1);
        std = Math.sqrt(std);

        // 标准化处理
        for (int i = 0; i < result.length; i++) {
            result[i] = (result[i] - avg) / std;
        }
        return result;
    }

    @Test
    void testMain() throws FileNotFoundException {
        Pair<double[][], double[]> fileData = getFileData("D:\\share\\data\\heart+disease\\new.data");
        // 数据清洗
        fileData = clean(fileData);
        long startTime = System.currentTimeMillis();

        double[][] transMatrix = feature(fileData.getKey());
        RealMatrix realMatrix = MatrixUtils.createRealMatrix(transMatrix);

        // 目标矩阵 = 原始数据矩阵 * 转换矩阵  完成降维与标准化操作
        RealMatrix multiply = MatrixUtils.createRealMatrix(fileData.getKey()).multiply(realMatrix);
        // 特征组合
        fileData = new Pair<>(multiply.getData(), fileData.getValue());
        System.out.println("根据互信息量求特征矩阵所用时间:" + (System.currentTimeMillis() - startTime) + "ms, 数据量:" + fileData.getKey().length + " * " + fileData.getKey()[0].length);

        // 70% 训练集
        // 30% 测试集
        int length = fileData.getValue().length;
        int transLength = (int) (length * 0.7);
        int testLength = length - transLength;

        double[][] transData = new double[transLength][];
        double[][] testData = new double[testLength][];
        System.arraycopy(fileData.getKey(), 0, transData, 0, transLength);
        System.arraycopy(fileData.getKey(), transLength, testData, 0, testLength);

        double[] transResult = new double[transLength];
        double[] testResult = new double[testLength];
        System.arraycopy(fileData.getValue(), 0, transResult, 0, transLength);
        System.arraycopy(fileData.getValue(), transLength, testResult, 0, testLength);

        // 训练集
        fileData = new Pair<>(transData, transResult);
        // 测试集
        Pair<double[][], double[]> testFileData = new Pair<>(testData, testResult);

        Map<Double[], Double> fileDataMap = new HashMap<>(fileData.getKey().length);
        for (int i = 0; i < fileData.getKey().length; i++) {
            double[] params = fileData.getKey()[i];
            double result = fileData.getValue()[i];
            fileDataMap.put(Arrays.stream(params).boxed().toArray(Double[]::new), result);
        }

        Map<Double[], Double> testDataMap = new HashMap<>(testFileData.getKey().length);
        for (int i = 0; i < testFileData.getKey().length; i++) {
            double[] params = testFileData.getKey()[i];
            double result = testFileData.getValue()[i];
            testDataMap.put(Arrays.stream(params).boxed().toArray(Double[]::new), result);
        }

        // 适应度函数
        FitnessHandler<Double[], Double> fitnessHandler = new HistoryDataLogisticFitnessHandler(testDataMap);
        // 种群/初始化
        Properties config = new Properties();
        config.setProperty("scenario.population.K", "1000");
        config.setProperty("scenario.population.init-percentage", "0.2");
        config.setProperty("scenario.population.init-cross-percentage", "0.4");
        config.setProperty("scenario.population.init-variation-percentage", "0.2");
        config.setProperty("scenario.population.learning-rate", "0.1");

        Population<Double[], Double> testPopulation = new RandomDnaPopulation(config, transData[0].length).init();

        NumberFormat instance = NumberFormat.getInstance();
        instance.setMaximumFractionDigits(8);

        List<Individual<Double[], Double>> maxIndividual = new ArrayList<>();
        double maxResult = 0;

        //
        List<Pair<Double[], Double>> learnParam = new ArrayList<>();
        for (Entry<Double[], Double> doubleEntry : fileDataMap.entrySet()) {
            learnParam.add(new Pair<>(doubleEntry.getKey(), doubleEntry.getValue()));
        }

        for (int i = 0; i < 1000; i++) {
            testPopulation.iteration(fitnessHandler, learnParam);
            if (i % 15 == 0) {
                List<Individual<Double[], Double>> topPercentage = fitnessHandler.findTopPercentage(testPopulation.allIndividuals(), 0.1, 10);
                double result = fitnessHandler.fitnessByMean(topPercentage);
                System.out.printf("第%d次迭代,   \t适应度为:%s", i, instance.format(result));
                if (maxResult < result) {
                    maxResult = result;
                    maxIndividual = topPercentage;
                    Individual<Double[], Double> doubleIndividual1 = topPercentage.get(0);
                    System.out.print("   新的突破!");
                    System.out.println("\t " + doubleIndividual1.toString());
                }
                System.out.println();
            }
        }
        Individual<Double[], Double> doubleIndividual = maxIndividual.get(0);
        int i = 1;


    }

    private Pair<double[][], double[]> makeTargetFileData() {
        double[][] doubles = new double[1000][];
        double[] result = new double[1000];
        for (int i = 0; i < 1000; i++) {
            doubles[i] = new double[13];
            double sum = 0;
            for (int j = 0; j < 13; j++) {
                doubles[i][j] = RandomUtils.nextDouble(0.0, 100);
                sum += j * doubles[i][j] + (j + 1) * doubles[i][j] * doubles[i][j];
            }
            result[i] = sum + 1;
        }
        return new Pair<>(doubles, result);
    }

    /**
     * 特征工程,注意,这里fileData中最后一列一般都是标签列,不参与特征工程
     *
     * @param rawData 数据,每行一个数据
     *
     * @return key->特征值矩阵 value 修改后的结果
     */
    private double[][] feature(double[][] rawData) {
        double[][] fileData = rawData;
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
        // 过滤掉不足特征值平均值1/10的特征
        sum /= 10 * dimensionLength;
        double finalSum = sum;
        double[][] transArray = eigList.stream().filter(t -> t.getKey() > finalSum).map(Pair::getValue).toArray(double[][]::new);
        RealMatrix transMatrix = MatrixUtils.createRealMatrix(transArray);
        // 转置为转换矩阵
        transMatrix = transMatrix.transpose();

        return transMatrix.getData();
    }

    @NotNull
    private Pair<double[][], double[]> clean(Pair<double[][], double[]> fileData) {
        if (CollectionUtil.isEmpty(fileData.getKey())) {
            return fileData;
        }
        // 过滤掉存在值为-9 的数据
        double[][] params = fileData.getKey();
        double[] result = fileData.getValue();
        List<Integer> removeIndex = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            if (!filter(params[i]) || !filter(result[i])) {
                removeIndex.add(i);
            }
        }
        double[][] realParams = new double[params.length - removeIndex.size()][];
        double[] realResults = new double[params.length - removeIndex.size()];
        int realIndex = 0;
        for (int i = 0; i < params.length; i++) {
            if (removeIndex.contains(i)) {
                continue;
            }
            realParams[realIndex] = params[i];
            realResults[realIndex] = result[i];
            realIndex++;
        }
        params = realParams;
        result = realResults;

        // 数据标准化
        params = extracted(params, null);
        // 二值数据不需要标准化
        //        result = extracted(result);
        return new Pair<>(params, result);
    }

    private boolean filter(double v) {
        return v != -9;
    }

    /**
     * 当前值中是否不存在-9
     *
     * @param data
     *
     * @return
     */
    private boolean filter(double[] data) {
        for (double datum : data) {
            if (datum == -9) {
                return false;
            }
        }
        return true;
    }

}
