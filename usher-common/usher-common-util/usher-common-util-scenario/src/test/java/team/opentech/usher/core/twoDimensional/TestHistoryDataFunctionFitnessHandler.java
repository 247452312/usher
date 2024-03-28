package team.opentech.usher.core.twoDimensional;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.core.fitness.AbstractDataFitnessHandler;
import team.opentech.usher.core.data.AbstractHistoryData;
import team.opentech.usher.core.data.AbstractHistoryMeanData;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月25日 16时24分
 */
public class TestHistoryDataFunctionFitnessHandler extends AbstractDataFitnessHandler<Float, Float> {

    private Random random = new Random();

    private Float[] dataKeys;


    public TestHistoryDataFunctionFitnessHandler(Map<Float, Float> testData) {
        super(testData);
        this.dataKeys = testData.keySet().toArray(new Float[0]);
        Arrays.sort(dataKeys);
    }

    @Override
    public Float[] calculateResult(Float[] params) {
        Float[] results = new Float[params.length];
        for (int i = 0; i < params.length; i++) {
            results[i] = calculateResult(params[i]);
        }
        return results;
    }

    @Override
    public Float calculateResult(Float param) {
        return fittingIndividual.findResult(param);
    }

    @Override
    protected AbstractHistoryData<Float, Float> makeFittingIndividual(Map<Float, Float> testData) {
        return new AbstractHistoryMeanData<Float, Float>(testData, new Float[0]) {

            @Override
            protected Float meanResult(Float param1, Float result1, Float param2, Float result2, Float param) {
                float proportion = (param - param1) / (param2 - param1);
                return proportion * (result2 - result1) + result1;
            }

            @Override
            protected int compare(Float o1, Float o2) {
                return o1.compareTo(o2);
            }
        };
    }

    @Override
    protected Float doQuantifyGap(Float historicalResult, Float calculationResult) {
        float abs = Math.abs(historicalResult - calculationResult);
        return historicalResult / (historicalResult + abs);
    }

    @NotNull
    @Override
    protected Float[] makeTestParams() {
        int size = 100;
        Float[] randomX = new Float[size];
        int randomSize = size / 2;
        for (int i = 0; i < randomSize; i++) {
            float x = random.nextInt(20) + random.nextFloat();
            randomX[i] = x;
        }
        int findSize = size - randomSize;
        for (int i = 0; i < findSize; i++) {
            randomX[randomSize + i] = dataKeys[random.nextInt(dataKeys.length)];
        }
        return randomX;
    }
}
