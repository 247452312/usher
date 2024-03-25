package team.opentech.usher.core;

import java.util.Random;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.fitness.AbstractCalculateFitnessHandler;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月20日 11时21分
 */
public class TestQuadraticFunctionFitnessHandler extends AbstractCalculateFitnessHandler<Float, Float> {

    private final static Random RANDOM = new Random();

    private final Integer a;

    private final Integer b;

    public TestQuadraticFunctionFitnessHandler(Integer a, Integer b) {
        this.a = a;
        this.b = b;
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
        return a * param * param + b;
    }

    @Override
    protected Float doQuantifyGap(Float standardResult, Float calculationResult) {
        float abs = Math.abs(standardResult - calculationResult);
        return 1 / (1 + abs);
    }

    @NotNull
    @Override
    protected Float[] makeTestParams() {
        int size = 100;
        Float[] randomX = new Float[size];
        for (int i = 0; i < size; i++) {
            float x = RANDOM.nextInt(20) + RANDOM.nextFloat();
            randomX[i] = x;
        }
        return randomX;
    }

}
