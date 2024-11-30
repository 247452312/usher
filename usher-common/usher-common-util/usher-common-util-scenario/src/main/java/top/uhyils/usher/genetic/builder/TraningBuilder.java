package top.uhyils.usher.genetic.builder;

import java.util.Properties;
import top.uhyils.usher.genetic.InitGenetic;
import top.uhyils.usher.genetic.LabeledDataGenetic;
import top.uhyils.usher.genetic.UsedGenetic;
import top.uhyils.usher.genetic.model.GeneticModel;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 08时00分
 */
public class TraningBuilder {

    /**
     * 训练集比例
     */
    private static final double TRAINING_RATE = 0.7;

    /**
     * 训练集
     */
    private Pair<double[][], double[]> trainingData;

    /**
     * 测试集
     */
    private Pair<double[][], double[]> testData;

    public TraningBuilder newBuilder() {
        return new TraningBuilder();
    }

    public TraningBuilder trainingData(double[][] data, double[] result) {
        this.trainingData = new Pair<>(data, result);
        return this;
    }

    public TraningBuilder testData(double[][] data, double[] result) {
        this.testData = new Pair<>(data, result);
        return this;
    }

    /**
     * 注入全部数据 默认训练集占70% 结果集占30%
     *
     * @param data   参数集
     * @param result 结果集
     *
     * @return
     */
    public TraningBuilder allData(double[][] data, double[] result) {
        return allData(data, result, TRAINING_RATE);
    }

    /**
     * 注入全部数据
     *
     * @param data      参数集
     * @param result    结果集
     * @param trainRate 训练集占比
     *
     * @return
     */
    public TraningBuilder allData(double[][] data, double[] result, double trainRate) {

        int length = result.length;
        int transLength = (int) (length * trainRate);
        int testLength = length - transLength;

        double[][] transData = new double[transLength][];
        double[][] testData = new double[testLength][];
        System.arraycopy(data, 0, transData, 0, transLength);
        System.arraycopy(data, transLength, testData, 0, testLength);

        double[] transResult = new double[transLength];
        double[] testResult = new double[testLength];
        System.arraycopy(result, 0, transResult, 0, transLength);
        System.arraycopy(result, transLength, testResult, 0, testLength);
        this.trainingData = new Pair<>(transData, transResult);
        this.testData = new Pair<>(testData, testResult);
        return this;
    }

    /**
     * 构造一个遗传算法
     *
     * @return
     */
    public InitGenetic buildGenetic(Properties config) {
        Asserts.assertTrue(trainingData != null && testData != null, "训练集和测试集不能为空");
        return new LabeledDataGenetic(trainingData, testData, config);
    }

    /**
     * 构造一个遗传算法
     *
     * @return
     */
    public UsedGenetic buildGenetic(GeneticModel geneticModel) {
        Asserts.assertTrue(testData != null, "历史数据集不能为空");
        return new LabeledDataGenetic(testData, geneticModel);
    }

    /**
     * 根据已知模型构造一个遗传算法
     *
     * @return
     */
    public InitGenetic buildGeneticByModel(GeneticModel model) {
        return new LabeledDataGenetic(testData, model);
    }

    protected Pair<double[][], double[]> getTestData() {
        return testData;
    }
}
