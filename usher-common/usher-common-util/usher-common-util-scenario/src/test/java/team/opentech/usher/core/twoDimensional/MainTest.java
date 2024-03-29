package team.opentech.usher.core.twoDimensional;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import org.junit.jupiter.api.Test;
import team.opentech.usher.FitnessHandler;
import team.opentech.usher.Individual;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月20日 10时52分
 */
public class MainTest {

    private static Random random = new Random();

    protected Double[] makeTestParams() {
        int size = 100;
        Double[] randomX = new Double[size];
        for (int i = 0; i < size; i++) {
            double x = random.nextInt(20) + random.nextDouble();
            randomX[i] = x;
        }
        return randomX;
    }

    @Test
    void scenarioCalculateTest() {
        FitnessHandler<Double, Double> fitnessHandler = new TestQuadraticFunctionFitnessHandler(8, 6);
        TestDoubleFunctionPopulation testPopulation = new TestDoubleFunctionPopulation(new Properties(), fitnessHandler);
        testPopulation.init();
        int lCount = 0;
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMaximumFractionDigits(8);
        float minAbs = 0.00005F;
        for (int i = 0; i < 1000; i++) {
            testPopulation.iteration();
            Double[] params = makeTestParams();
            List<Individual<Double, Double>> topPercentage = fitnessHandler.findTopPercentage(testPopulation.allIndividuals(), 0.1, 10);
            double result = fitnessHandler.fitnessByMean(topPercentage, params);

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
    void scenarioHistoryDataTest() {
        Map<Double, Double> testData = new HashMap<>();
        for (int i = 0; i < 512; i++) {
            double v = i / 20F + random.nextFloat();
            testData.put(v, 8 * v * v + 6);
        }
        FitnessHandler<Double, Double> fitnessHandler = new TestHistoryDataFunctionFitnessHandler(testData);

        TestDoubleFunctionPopulation testPopulation = new TestDoubleFunctionPopulation(new Properties(), fitnessHandler);
        testPopulation.init();
        int lCount = 0;
        double minAbs = .98;
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMaximumFractionDigits(8);
        for (int i = 0; i < 1000; i++) {
            testPopulation.iteration();
            Double[] params = makeTestParams();
            List<Individual<Double, Double>> topPercentage = fitnessHandler.findTopPercentage(testPopulation.allIndividuals(), 0.1, 10);
            double result = fitnessHandler.fitnessByMean(topPercentage, params);

            System.out.printf("第%d次迭代,   \t适应度为:%s", i, instance.format(result));
            System.out.println();
            if (result > minAbs) {
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
}
