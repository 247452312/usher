package team.opentech.usher.core.heartDisease;

import java.util.Arrays;
import java.util.Map;
import org.apache.commons.lang3.RandomUtils;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.core.data.AbstractHistoryData;
import team.opentech.usher.core.data.AbstractHistoryMeanData;
import team.opentech.usher.core.fitness.AbstractDataFitnessHandler;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月27日 14时52分
 */
public class TestHeartHistoryDataFunctionFitnessHandler extends AbstractDataFitnessHandler<Double[], Double> {

    public TestHeartHistoryDataFunctionFitnessHandler(Map<Double[], Double> testData) {
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
                for (int i = 0; i < 14; i++) {
                    double v = (param[i] - param1[i]) / (param2[i] - param1[i]);
                    sum += v;
                }
                return sum / 14 * (result2 - result1) + result1;
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
    protected Double doQuantifyGap(Double historicalResult, Double calculationResult) {
        return 1 / (1 + Math.abs(historicalResult - calculationResult));
    }

    @NotNull
    @Override
    protected Double[][] makeTestParams() {
        int length = testData.size() / 10;
        Double[][] result = new Double[length][];
        Double[][] array = testData.keySet().toArray(new Double[0][]);
        for (int i = 0; i < length; i++) {
            result[i] = array[RandomUtils.nextInt(0, length)];
        }
        return result;
    }
}
