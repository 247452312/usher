package team.opentech.usher.core.twoDimensional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import team.opentech.usher.core.AbstractIndividual;
import team.opentech.usher.lang.LongByte;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月19日 14时56分
 */
public class TestQuadraticFunctionIndividual extends AbstractIndividual<Double, Double> {


    private Map<Double, Double> cacheResult = new HashMap<>();

    protected TestQuadraticFunctionIndividual() {
        this(new Random());
    }

    protected TestQuadraticFunctionIndividual(Random random) {
        // ax^2 + b 权重占3bit 系数占8bit 从低到高格式为 权重三位 系数8位 权重3位 系数8位
        super(findDna(random), findDna(random));
    }

    public TestQuadraticFunctionIndividual(LongByte firstDna, LongByte secondDna) {
        super(firstDna, secondDna);
    }

    private static LongByte findDna(Random random) {
        return new LongByte(new int[]{random.nextInt(Integer.MAX_VALUE)});
    }

    @Override
    public void variation(byte[] virusDna) {
        cacheResult.clear();
        super.variation(virusDna);
    }

    @Override
    public Double findResult(Double param) {
        if (cacheResult.containsKey(param)) {
            return cacheResult.get(param);
        }
        LongByte firstDna = firstDna();
        LongByte secondDna = secondDna();
        LongByte firstPower = firstDna.get(0, 3);
        LongByte secondPower = secondDna.get(0, 3);

        LongByte a = firstPower.compareTo(secondPower) >= 0 ? firstDna.get(3, 8) : secondDna.get(3, 8);

        firstPower = firstDna.get(11, 3);
        secondPower = secondDna.get(11, 3);
        LongByte b = firstPower.compareTo(secondPower) >= 0 ? firstDna.get(14, 8) : secondDna.get(14, 8);

        double v = a.intValue() * param * param + b.intValue();
        cacheResult.put(param, v);
        return v;
    }

    @Override
    public String toString() {
        LongByte firstDna = firstDna();
        LongByte secondDna = secondDna();
        LongByte firstPower = firstDna.get(0, 3);
        LongByte secondPower = secondDna.get(0, 3);

        LongByte a = firstPower.compareTo(secondPower) >= 0 ? firstDna.get(3, 8) : secondDna.get(3, 8);

        firstPower = firstDna.get(11, 3);
        secondPower = secondDna.get(11, 3);
        LongByte b = firstPower.compareTo(secondPower) >= 0 ? firstDna.get(14, 8) : secondDna.get(14, 8);
        return a.intValue() + " * x^2 + " + b.intValue();
    }

    @Override
    protected TestQuadraticFunctionIndividual makeNewIndividual(LongByte firstDna, LongByte secondDna) {
        return new TestQuadraticFunctionIndividual(firstDna, secondDna);
    }
}
