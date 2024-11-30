package top.uhyils.usher.genetic.core.fitness;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.RandomUtils;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.genetic.core.data.AbstractHistoryData;
import top.uhyils.usher.genetic.core.data.AbstractHistoryMeanData;

/**
 * 二值适应度函数 Logistic
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月27日 14时52分
 */
public class HistoryDataLogisticFitnessHandler extends AbstractDataFitnessHandler<Double[], Double> {

    private static final Double MAX_LOSS = 100.0;

    public HistoryDataLogisticFitnessHandler(Map<Double[], Double> testData) {
        super(testData);
    }

    @Override
    public Double[] calculateResult(Double[][] params) {
        Double[] integers = new Double[params.length];
        for (int i = 0; i < params.length; i++) {
            integers[i] = calculateResult(params[i]);
        }
        return integers;
    }

    @Override
    protected AbstractHistoryData<Double[], Double> makeFittingIndividual(Map<Double[], Double> testData) {
        return new AbstractHistoryMeanData<Double[], Double>(testData, new Double[0][]) {


            @Override
            protected Double meanResult(Double[] param1, Double result1, Double[] param2, Double result2, Double[] param) {
                double sum = 0;
                for (int i = 0; i < param.length; i++) {
                    double v = (param[i] - param1[i]) / (param2[i] - param1[i]);
                    sum += v;
                }
                return sum / param.length * (result2 - result1) + result1;
            }

            @Override
            protected int compare(Double[] o1, Double[] o2) {
                double o1sum = Arrays.stream(o1).mapToDouble(t -> t).sum();
                double o2sum = Arrays.stream(o2).mapToDouble(t -> t).sum();
                return Double.compare(o1sum, o2sum);
            }
        };
    }

    @Override
    protected Double loss(Double historicalResult, Double calculationResult) {
        if (Objects.equals(historicalResult, calculationResult)) {
            return 0.0;
        }
        if (Objects.equals(historicalResult, 0.0)) {
            if (Objects.equals(calculationResult, 1.0)) {
                return MAX_LOSS;
            }
            return Math.min(-Math.log(1 - calculationResult), MAX_LOSS);
        } else {
            if (Objects.equals(calculationResult, 0.0)) {
                return MAX_LOSS;
            }
            return Math.min(-Math.log(calculationResult), MAX_LOSS);
        }

        // todo 这里理论上要区分损失函数,损失函数需要传入?
        // 二值数据集损失函数为 logistic回归函数 f(x) = -y * log(h(x)) - (1-y) * log(1-h(x))
        //        return 0.5 * Math.pow(historicalResult - calculationResult, 2);
        //        return -historicalResult * Math.log(calculationResult) - (1 - historicalResult) * Math.log(1 - calculationResult);
    }

    @NotNull
    @Override
    protected Double[][] testParams() {
        // 测试集随机选出1/10去测试
        int length = testData.size() / 10;
        Double[][] result = new Double[length][];
        Double[][] array = testData.keySet().toArray(new Double[0][]);
        for (int i = 0; i < length; i++) {
            result[i] = array[RandomUtils.nextInt(0, length)];
        }
        return result;
    }

    @NotNull
    @Override
    protected Double[][] testAllParams() {
        return testData.keySet().toArray(new Double[0][]);
    }
}
