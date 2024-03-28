package team.opentech.usher.core.heartDisease;

import java.util.Map;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.core.data.AbstractHistoryData;
import team.opentech.usher.core.data.AbstractHistoryMeanData;
import team.opentech.usher.core.fitness.AbstractDataFitnessHandler;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月27日 14时52分
 */
public class TestHeartHistoryDataFunctionFitnessHandler extends AbstractDataFitnessHandler<Float[], Float> {

    public TestHeartHistoryDataFunctionFitnessHandler(Map<Float[], Float> testData) {
        super(testData);
    }

    @Override
    public Float[] calculateResult(Float[][] params) {
        Float[] integers = new Float[params.length];
        for (int i = 0; i < params.length; i++) {
            integers[i] = calculateResult(params[i]);
        }
        return integers;
    }

    @Override
    public Float calculateResult(Float[] param) {
        //        return asd;
        // todo 如何计算结果
        return null;
    }

    @Override
    protected AbstractHistoryData<Float[], Float> makeFittingIndividual(Map<Float[], Float> testData) {
        return new AbstractHistoryMeanData<Float[], Float>(testData, new Float[0][]) {

            @Override
            protected Float meanResult(Float[] param1, Float result1, Float[] param2, Float result2, Float[] param) {
                float sum = 0;
                for (int i = 0; i < 14; i++) {
                    float v = (param[i] - param1[i]) / (param2[i] - param1[i]);
                    sum += v;
                }
                return sum / 14 * (result2 - result1) + result1;
            }

            @Override
            protected int compare(Float[] o1, Float[] o2) {
                float o1sum = 0;
                float o2sum = 0;
                for (int i = 0; i < 14; i++) {
                    o1sum += o1[i] * o1[i];
                    o2sum += o2[i] * o2[i];
                }
                return Float.compare(o1sum, o2sum);
            }
        };
    }

    @Override
    protected Float doQuantifyGap(Float historicalResult, Float calculationResult) {
        return 1 / (1 + Math.abs(historicalResult - calculationResult));
    }

    @NotNull
    @Override
    protected Float[][] makeTestParams() {
        return testData.keySet().toArray(new Float[0][]);
    }
}
