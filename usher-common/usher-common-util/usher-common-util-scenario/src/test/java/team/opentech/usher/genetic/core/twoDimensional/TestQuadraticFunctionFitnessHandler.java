package team.opentech.usher.genetic.core.twoDimensional;

import java.util.Random;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.genetic.core.fitness.AbstractCalculateFitnessHandler;

/**
 * 二维适应度函数
 * a * x^2 + b
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月20日 11时21分
 */
public class TestQuadraticFunctionFitnessHandler extends AbstractCalculateFitnessHandler<Double, Double> {

    private final static Random RANDOM = new Random();

    private final Integer a;

    private final Integer b;

    public TestQuadraticFunctionFitnessHandler(Integer a, Integer b) {
        this.a = a;
        this.b = b;
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
        return a * param * param + b;
    }

    @Override
    protected Double loss(Double standardResult, Double calculationResult) {
        return 0.5 * Math.pow(standardResult - calculationResult, 2);
    }

    @NotNull
    @Override
    protected Double[] testParams() {
        int size = 100;
        Double[] randomX = new Double[size];
        for (int i = 0; i < size; i++) {
            double x = RANDOM.nextInt(20) + RANDOM.nextDouble();
            randomX[i] = x;
        }
        return randomX;
    }

}
