package team.opentech.usher.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import team.opentech.usher.FitnessHandler;
import team.opentech.usher.Individual;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月20日 11时21分
 */
public class TestFitnessHandler implements FitnessHandler<Float, Float> {

    private final static Random RANDOM = new Random();

    private final Integer a;

    private final Integer b;

    public TestFitnessHandler(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Float fitness(Individual<Float, Float> individual) {
        int size = 100;
        float[] randomX = new float[size];
        for (int i = 0; i < size; i++) {
            float x = RANDOM.nextFloat();
            randomX[i] = x;
        }
        return fitness(individual, randomX);
    }

    @Override
    public List<Individual<Float, Float>> findTopPercentage(List<Individual<Float, Float>> individuals, Float percentage, Integer min) {

        int targetSize = (int) (individuals.size() * percentage);
        int realTargetSize = Math.max(targetSize, min);
        realTargetSize = Math.min(realTargetSize, individuals.size());
        int size = 100;
        float[] randomX = new float[size];
        for (int i = 0; i < size; i++) {
            float x = RANDOM.nextFloat();
            randomX[i] = x;
        }
        individuals.sort(Comparator.comparingDouble(value -> -fitness(value, randomX)));
        List<Individual<Float, Float>> result = new ArrayList<>(realTargetSize);
        for (int i = 0; i < realTargetSize; i++) {
            result.add(individuals.get(i));
        }
        return result;
    }

    /**
     * 标准值/目标值
     *
     * @param x
     *
     * @return
     */
    public float findRealResult(float x) {
        return a * x * x + b;
    }

    @Override
    public Float fitness(Individual<Float, Float> individual, float i) {
        float targetResult = findRealResult(i);
        Float realResult = individual.findResult(i);
        float abs = Math.abs(targetResult - realResult);
        return 1 / (1 + abs);
    }

    @Override
    public Float fitness(Individual<Float, Float> individual, float[] i) {
        float sum = 0;
        for (float v : i) {
            sum += fitness(individual, v);
        }
        return sum / i.length;
    }
}
