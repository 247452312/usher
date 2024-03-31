package team.opentech.usher.core.twoDimensional;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.core.data.AbstractHistoryData;
import team.opentech.usher.core.data.AbstractHistoryMeanData;
import team.opentech.usher.core.fitness.AbstractDataFitnessHandler;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月25日 16时24分
 */
public class TestHistoryDataFunctionFitnessHandler extends AbstractDataFitnessHandler<Double, Double> {

    private Random random = new Random();

    private Double[] dataKeys;


    public TestHistoryDataFunctionFitnessHandler(Map<Double, Double> testData) {
        super(testData);
        this.dataKeys = testData.keySet().toArray(new Double[0]);
        Arrays.sort(dataKeys);
    }

    @Override
    public Double[] calculateResult(Double[] params) {
        Double[] results = new Double[params.length];
        for (int i = 0; i < params.length; i++) {
            results[i] = calculateResult(params[i]);
        }
        return results;
    }

    @Override
    public Double calculateResult(Double param) {
        return fittingIndividual.findResult(param);
    }

    @Override
    protected AbstractHistoryData<Double, Double> makeFittingIndividual(Map<Double, Double> testData) {
        return new AbstractHistoryMeanData<Double, Double>(testData, new Double[0]) {

            @Override
            protected Double meanResult(Double param1, Double result1, Double param2, Double result2, Double param) {
                double proportion = (param - param1) / (param2 - param1);
                return proportion * (result2 - result1) + result1;
            }

            @Override
            protected int compare(Double o1, Double o2) {
                return o1.compareTo(o2);
            }

        };
    }

    @Override
    protected Double doQuantifyGap(Double historicalResult, Double calculationResult) {
        double abs = Math.abs(historicalResult - calculationResult);
        return historicalResult / (historicalResult + abs);
    }

    @NotNull
    @Override
    protected Double[] makeTestParams() {
        int size = 100;
        Double[] randomX = new Double[size];
        int randomSize = size / 2;
        for (int i = 0; i < randomSize; i++) {
            double x = random.nextInt(20) + random.nextDouble();
            randomX[i] = x;
        }
        int findSize = size - randomSize;
        for (int i = 0; i < findSize; i++) {
            randomX[randomSize + i] = dataKeys[random.nextInt(dataKeys.length)];
        }
        return randomX;
    }
}
