package top.uhyils.usher.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import top.uhyils.usher.enums.DimensionalityReductionTypeEnum;
import top.uhyils.usher.enums.StandardizationTypeEnum;
import top.uhyils.usher.genetic.core.fitness.FitnessHandler;
import top.uhyils.usher.genetic.core.fitness.HistoryDataLogisticFitnessHandler;
import top.uhyils.usher.genetic.core.individual.Individual;
import top.uhyils.usher.genetic.core.population.Population;
import top.uhyils.usher.genetic.core.population.RandomDnaPopulation;
import top.uhyils.usher.genetic.model.GeneticModel;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.Pair;

/**
 * 有标签数据的遗传算法
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 08时11分
 */
public class LabeledDataGenetic implements InitGenetic, UsedGenetic {

    /**
     * 配置
     */
    private final Properties populationConfig;

    /**
     * 训练集
     */
    private Pair<double[][], double[]> trainingData;

    /**
     * 测试集
     */
    private Pair<double[][], double[]> testData;

    /**
     * 降维方法类型
     */
    private DimensionalityReductionTypeEnum dimensionalityReductionType;

    /**
     * 降维矩阵
     */
    private double[][] dimensionalityReductionMatrix;

    /**
     * 标准化方法
     */
    private StandardizationTypeEnum standardizationType;

    /**
     * 结果集是否标准化
     */
    private boolean resultStandardized;

    /**
     * 适应度函数
     */
    private FitnessHandler<Double[], Double> fitnessHandler;

    /**
     * 种群
     */
    private Population<Double[], Double> population;


    /**
     * 构造
     *
     * @param trainingData     训练集
     * @param testData         测试集
     * @param populationConfig 配置
     */
    public LabeledDataGenetic(Pair<double[][], double[]> trainingData, Pair<double[][], double[]> testData, Properties populationConfig) {
        this.trainingData = trainingData;
        this.testData = testData;
        this.populationConfig = populationConfig;
    }


    public LabeledDataGenetic(Pair<double[][], double[]> historyData, GeneticModel model) {
        this.testData = historyData;
        this.populationConfig = model.getPopulationConfig();
        this.dimensionalityReductionType = model.getDimensionalityReductionType();
        this.dimensionalityReductionMatrix = model.getDimensionalityReductionMatrix();
        this.standardizationType = model.getStandardizationType();
        this.resultStandardized = model.getResultStandardized();

        Map<Double[], Double> testDataMap = new HashMap<>(this.testData.getKey().length);
        for (int i = 0; i < this.testData.getKey().length; i++) {
            double[] params = this.testData.getKey()[i];
            double result = this.testData.getValue()[i];
            testDataMap.put(Arrays.stream(params).boxed().toArray(Double[]::new), result);
        }

        // 适应度函数
        this.fitnessHandler = new HistoryDataLogisticFitnessHandler(testDataMap);
        // 种群/初始化
        this.population = new RandomDnaPopulation(populationConfig, model.getIndividuals());

    }

    @Override
    public double train(int times, Boolean log) {
        // 第一次进来,需要初始化
        if (population == null) {
            init();
        }
        for (int i = 0; i < times; i++) {
            population.iteration(fitnessHandler);
            log(times, log, i);
        }
        List<Individual<Double[], Double>> topPercentage = fitnessHandler.findTopPercentage(population.allIndividuals(), 0.1, 10);
        return fitnessHandler.fitnessByMean(topPercentage);
    }

    @Override
    public double trainAndLearnByTrainData(int times, Boolean log) {
        // 第一次进来,需要初始化
        if (population == null) {
            init();
        }
        // 训练集拿出来当做学习对象
        List<Pair<Double[], Double>> learnParam = new ArrayList<>();
        for (int i = 0; i < trainingData.getKey().length; i++) {
            Double[] doubles = Arrays.stream(trainingData.getKey()[i]).boxed().toArray(Double[]::new);
            Double result = trainingData.getValue()[i];
            learnParam.add(new Pair<>(doubles, result));
        }
        for (int i = 0; i < times; i++) {
            population.iteration(fitnessHandler, learnParam);
            log(times, log, i);
        }
        List<Individual<Double[], Double>> topPercentage = fitnessHandler.findTopPercentage(population.allIndividuals(), 0.1, 10);
        return fitnessHandler.fitnessByMean(topPercentage);
    }

    @Override
    public void setDimensionalityReductionConfig(DimensionalityReductionTypeEnum type) {
        Asserts.assertTrue(this.dimensionalityReductionType == null, "已存在降维类型,不可更改");
        this.dimensionalityReductionType = type;
    }

    @Override
    public void setExtractedConfig(StandardizationTypeEnum type, boolean resultStandardized) {
        Asserts.assertTrue(this.standardizationType == null, "已存在数据标准化类型,不可更改");
        this.standardizationType = type;
        this.resultStandardized = resultStandardized;
    }

    @Override
    public double[][] extracted(double[][] data, int[] needDimensionIndex) {
        return this.standardizationType.extracted(data, needDimensionIndex);
    }

    @Override
    public double[] extracted(double[] data) {
        return this.standardizationType.extracted(data);
    }

    @Override
    public Double test(Double[] param) {
        return population.findResult(fitnessHandler, param);
    }

    @Override
    public GeneticModel exportModel() {
        GeneticModel model = new GeneticModel();
        model.setPopulationConfig(populationConfig);
        model.setDimensionalityReductionType(dimensionalityReductionType);
        model.setDimensionalityReductionMatrix(dimensionalityReductionMatrix);
        model.setStandardizationType(standardizationType);
        model.setResultStandardized(resultStandardized);
        model.setIndividuals(population.allIndividuals());
        return model;
    }

    /**
     * 根据配置降维
     */
    void dimensionalityReduction() {
        Asserts.assertTrue(this.trainingData != null && this.testData != null, "训练集与测试集不能为空");
        this.dimensionalityReductionMatrix = this.dimensionalityReductionType.createDimensionalityReductionMatrix(this.trainingData.getKey());
        this.trainingData.setKey(DimensionalityReductionTypeEnum.dimensionalityReduction(this.trainingData.getKey(), dimensionalityReductionMatrix));
        this.testData.setKey(DimensionalityReductionTypeEnum.dimensionalityReduction(this.testData.getKey(), dimensionalityReductionMatrix));
    }

    private void log(int times, Boolean log, int i) {
        if (log && i % (times / 10) == 0) {
            List<Individual<Double[], Double>> topPercentage = fitnessHandler.findTopPercentage(population.allIndividuals(), 0.1, 10);
            double result = fitnessHandler.fitnessByMean(topPercentage);
            System.out.println("第" + (i + 1) + "次迭代");
            System.out.println("当前适应度为:" + result);
            Individual<Double[], Double> bestPercentage = topPercentage.get(0);
            System.out.println("当前最优个体: " + bestPercentage);
            double[][] key = testData.getKey();
            double[] value = testData.getValue();
            int count = value.length * topPercentage.size();
            int successCount = 0;
            for (int j = 0; j < key.length; j++) {
                Double[] doubles = Arrays.stream(key[j]).boxed().toArray(Double[]::new);
                for (Individual<Double[], Double> doubleIndividual : topPercentage) {
                    double clcResult = doubleIndividual.findResult(doubles);
                    double realResult = value[j];
                    if (Math.round(clcResult) == Math.round(realResult)) {
                        successCount++;
                    }
                }
            }
            System.out.println("较优个体计算值正确率: " + (successCount * 100 / count) + "%");
        }
    }

    /**
     * 标准化自身数据
     */
    private void extractedSelf() {
        this.trainingData.setKey(extracted(this.trainingData.getKey(), null));
        this.testData.setKey(extracted(this.testData.getKey(), null));
        if (this.resultStandardized) {
            this.trainingData.setValue(extracted(this.trainingData.getValue()));
            this.testData.setValue(extracted(this.testData.getValue()));
        }
    }

    /**
     * 初始化
     */
    private void init() {
        Asserts.assertTrue(this.trainingData != null && this.testData != null, "训练集与测试集不能为空");
        // 对训练集,测试集进行降维
        dimensionalityReduction();
        // 标准化测试集和训练集
        extractedSelf();
        Map<Double[], Double> testDataMap = new HashMap<>(testData.getKey().length);
        for (int i = 0; i < testData.getKey().length; i++) {
            double[] params = testData.getKey()[i];
            double result = testData.getValue()[i];
            testDataMap.put(Arrays.stream(params).boxed().toArray(Double[]::new), result);
        }

        // 适应度函数
        this.fitnessHandler = new HistoryDataLogisticFitnessHandler(testDataMap);
        // 种群/初始化
        this.population = new RandomDnaPopulation(populationConfig, trainingData.getKey()[0].length);
        this.population.init();
    }

}
