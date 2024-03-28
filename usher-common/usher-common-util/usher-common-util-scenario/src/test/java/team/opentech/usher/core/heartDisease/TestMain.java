package team.opentech.usher.core.heartDisease;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.ejml.simple.SimpleMatrix;
import org.junit.jupiter.api.Test;
import team.opentech.usher.FitnessHandler;
import team.opentech.usher.Individual;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.util.CollectionUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月26日 09时27分
 */
public class TestMain {


    @NotNull
    private static List<Float[]> getFileData(String path) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        String fileData = dataReader.read(path);
        String[] person = fileData.split("\\n");

        List<Float[]> result = new ArrayList<>();
        for (String s : person) {
            String[] split = s.trim().split(" ");
            if (split.length < 66) {
                break;
            }
            Float[] item = new Float[14];
            item[0] = Float.valueOf(split[2].trim());
            item[1] = Float.valueOf(split[3].trim());
            item[2] = Float.valueOf(split[8].trim());
            item[3] = Float.valueOf(split[9].trim());
            item[4] = Float.valueOf(split[11].trim());
            item[5] = Float.valueOf(split[15].trim());
            item[6] = Float.valueOf(split[18].trim());
            item[7] = Float.valueOf(split[31].trim());
            item[8] = Float.valueOf(split[37].trim());
            item[9] = Float.valueOf(split[39].trim());
            item[10] = Float.valueOf(split[40].trim());
            item[11] = Float.valueOf(split[43].trim());
            item[12] = Float.valueOf(split[50].trim());
            item[13] = Float.valueOf(split[57].trim());
            result.add(item);
        }
        return result;
    }

    private static List<Float[]> extracted(List<Float[]> fileData) {
        Float[] floats = fileData.get(0);
        // 求均值
        float[] avg = new float[floats.length];
        for (Float[] fileDatum : fileData) {
            for (int i = 0; i < fileDatum.length; i++) {
                avg[i] += fileDatum[i];
            }
        }
        for (int i = 0; i < avg.length; i++) {
            avg[i] = avg[i] / fileData.size();
        }
        // 求方差
        float[] variance = new float[floats.length];
        for (Float[] fileDatum : fileData) {
            for (int i = 0; i < fileDatum.length; i++) {
                float v = fileDatum[i] - avg[i];
                variance[i] += v * v;
            }
        }
        for (int i = 0; i < variance.length; i++) {
            variance[i] = variance[i] / (fileData.size() - 1);
            variance[i] = (float) Math.sqrt(variance[i]);
        }

        // 标准化处理
        for (Float[] item : fileData) {
            for (int j = 0; j < item.length; j++) {
                item[j] = (item[j] - avg[j]) / variance[j];
            }
        }
        return fileData;

    }

    @Test
    void testMain() throws FileNotFoundException {
        List<Float[]> fileData = getFileData("C:\\Users\\Lenovo\\Downloads\\heart+disease\\new.data");
        // 数据清洗
        fileData = clean(fileData);
        fileData = feature(fileData);
        Map<Float[], Float> transDataMap = new HashMap<>();
        for (Float[] fileDatum : fileData) {
            transDataMap.put(fileDatum, fileDatum[fileDatum.length - 1]);
        }
        Float[][] testData = transDataMap.keySet().toArray(new Float[0][]);

        FitnessHandler<Float[], Float> fitnessHandler = new TestHeartHistoryDataFunctionFitnessHandler(transDataMap);

        TestHeartFunctionPopulation testPopulation = new TestHeartFunctionPopulation(new Properties(), fitnessHandler);
        testPopulation.init();
        int lCount = 0;
        float minAbs = 0.0005F;
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMaximumFractionDigits(8);
        for (int i = 0; i < 1000; i++) {
            testPopulation.iteration();

            List<Individual<Float[], Float>> topPercentage = fitnessHandler.findTopPercentage(testPopulation.allIndividuals(), 0.1F, 10);
            float result = 1 - fitnessHandler.fitnessByMean(topPercentage, testData);
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

    /**
     * 特征工程
     *
     * @param fileData
     *
     * @return
     */
    private List<Float[]> feature(List<Float[]> fileData) {
        Float[] floats = fileData.get(0);
        // 降维&正交化
        // 1.计算相关系数矩阵时这里使用香农信息论中的互信息理论
        // 互信息理论中 相关系数公式为 相关系数=2I(x,y)/(H(x),H(y))  其中 I(x,y) = H(x) + H(y) - H(x,y)
        // 里面 H(x) = E[log(1/P(x))] = ∑P(xi)log(1/P(xi))
        // H(x,y) = E[log(1/P(xy))] = ∑P(xi,yi)log(1/P(xi,yi))
        // 其中p(x) 代表概率 高维中某一维的概率计算方式应该遵循 将[最小值, 最大值] 平均分为100份, 落在同一份中的值属于同一区域的概率,计算方式也变成了计算当前区域的概率

        // 编程计算 获取各个维度的最大值最小值 并求 方块值 = 差值/100
        int dimensionLength = floats.length;
        float[] max = new float[dimensionLength];
        float[] min = new float[dimensionLength];
        for (Float[] fileDatum : fileData) {
            for (int i = 0; i < fileDatum.length; i++) {
                if (fileDatum[i] > max[i]) {
                    max[i] = fileDatum[i];
                }
                if (fileDatum[i] < min[i]) {
                    min[i] = fileDatum[i];
                }
            }
        }
        float[] standardLength = new float[dimensionLength];
        for (int i = 0; i < max.length; i++) {
            standardLength[i] = (max[i] - min[i]) / 100;
        }

        // 计算每个维度里每个标准长度的概率
        float[][] p = new float[dimensionLength][100];
        for (Float[] fileDatum : fileData) {
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
                p[i][j] = p[i][j] / 100;
            }
        }

        // 求每一个维度的信息熵
        float[] h = new float[dimensionLength];
        for (int i = 0; i < dimensionLength; i++) {
            // 第i个维度的每个标准长度概率
            float[] pi = p[i];
            for (float v : pi) {
                h[i] += -v * Math.log(v);
            }
        }

        // 求联合概率 这里的四个维度分别为 维度a 维度b a标准长度所在 b标准长度所在
        float[][][][] pxy = new float[dimensionLength][dimensionLength][100][100];
        for (Float[] fileDatum : fileData) {
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
        for (float[][][] item1 : pxy) {
            for (float[][] item2 : item1) {
                for (float[] item3 : item2) {
                    for (int i = 0; i < item3.length; i++) {
                        item3[i] = item3[i] / 10000;
                    }
                }
            }
        }
        // 求联合信息熵
        float[][] hxy = new float[dimensionLength][dimensionLength];
        for (int i = 0; i < dimensionLength; i++) {
            for (int j = 0; j < dimensionLength; j++) {
                // 维度i 和维度j 的所有概率
                float[][] pij = pxy[i][j];
                for (int i1 = 0; i1 < pij.length; i1++) {
                    for (int j1 = 0; j1 < pij[i1].length; j1++) {
                        hxy[i][j] += -pij[i1][j1] * Math.log(pij[i1][j1]);
                    }
                }
            }
        }

        // 求最终的香农互信息矩阵 可以替代相关系数矩阵
        double[][] result = new double[dimensionLength][dimensionLength];
        for (int i = 0; i < dimensionLength; i++) {
            for (int j = 0; j < i; j++) {
                float ixy = h[i] + h[j] - hxy[i][j];
                result[i][j] = ixy * 2 / (h[i] + h[j]);
            }
        }
        SimpleMatrix matrix = new SimpleMatrix(result);
        matrix.eig();
        // 特征组合
        return fileData;
    }

    @NotNull
    private List<Float[]> clean(List<Float[]> fileData) {
        if (CollectionUtil.isEmpty(fileData)) {
            return fileData;
        }
        // 过滤掉存在值为-9 的数据
        fileData = fileData.stream().filter(this::filter).collect(Collectors.toList());
        // 数据标准化
        fileData = extracted(fileData);
        return fileData;
    }

    private boolean filter(Float[] data) {
        for (Float datum : data) {
            if (datum == -9) {
                return false;
            }
        }
        return true;
    }

}
