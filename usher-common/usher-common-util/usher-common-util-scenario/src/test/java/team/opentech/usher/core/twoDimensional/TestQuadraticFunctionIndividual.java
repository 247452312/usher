package team.opentech.usher.core.twoDimensional;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import team.opentech.usher.core.AbstractIndividual;
import team.opentech.usher.util.BitSetUtil;

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

    public TestQuadraticFunctionIndividual(BitSet firstDna, BitSet secondDna) {
        super(firstDna, secondDna);
    }

    public TestQuadraticFunctionIndividual(BitSet firstDna, BitSet secondDna, int size) {
        super(firstDna, secondDna, size);
    }

    private static BitSet findDna(Random random) {
        return BitSetUtil.valueOf(random.nextInt(Integer.MAX_VALUE));
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
        BitSet firstDna = firstDna();
        BitSet secondDna = secondDna();
        BitSet firstPower = firstDna.get(0, 3);
        BitSet secondPower = secondDna.get(0, 3);

        BitSet a = BitSetUtil.compareTo(firstPower, secondPower) >= 0 ? firstDna.get(3, 11) : secondDna.get(3, 11);

        firstPower = firstDna.get(11, 14);
        secondPower = secondDna.get(11, 14);
        BitSet b = BitSetUtil.compareTo(firstPower, secondPower) >= 0 ? firstDna.get(14, 22) : secondDna.get(14, 22);

        double v = BitSetUtil.toInt(a) * param * param + BitSetUtil.toInt(b);
        cacheResult.put(param, v);
        return v;
    }

    @Override
    public String toString() {
        BitSet firstDna = firstDna();
        BitSet secondDna = secondDna();
        BitSet firstPower = firstDna.get(0, 3);
        BitSet secondPower = secondDna.get(0, 3);

        BitSet a = BitSetUtil.compareTo(firstPower, secondPower) >= 0 ? firstDna.get(3, 11) : secondDna.get(3, 11);

        firstPower = firstDna.get(11, 14);
        secondPower = secondDna.get(11, 14);
        BitSet b = BitSetUtil.compareTo(firstPower, secondPower) >= 0 ? firstDna.get(14, 22) : secondDna.get(14, 22);
        return BitSetUtil.toInt(a) + " * x^2 + " + BitSetUtil.toInt(b);
    }

    @Override
    protected void dealDiff(Double result, Double targetResult, Double learningRate) {
        // 这里暂时不考虑有方向学习
    }

    @Override
    protected TestQuadraticFunctionIndividual makeNewIndividual(BitSet firstDna, BitSet secondDna, int size) {
        return new TestQuadraticFunctionIndividual(firstDna, secondDna, size);
    }
}
