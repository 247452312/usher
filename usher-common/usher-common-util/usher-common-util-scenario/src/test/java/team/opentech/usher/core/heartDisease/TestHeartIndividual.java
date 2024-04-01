package team.opentech.usher.core.heartDisease;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import team.opentech.usher.Individual;
import team.opentech.usher.core.AbstractIndividual;
import team.opentech.usher.util.BitSetUtil;
import team.opentech.usher.util.Pair;
import team.opentech.usher.util.StringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月27日 15时19分
 */
public class TestHeartIndividual extends AbstractIndividual<Double[], Double> {

    protected TestHeartIndividual(int size) {
        super(size);
    }

    public TestHeartIndividual(BitSet firstDna, BitSet secondDna, int size) {
        super(firstDna, secondDna, size);
    }

    @Override
    public Double findResult(Double[] param) {
        double result = 0;
        BitSet firstBit = firstDna();
        BitSet secondBit = secondDna();
        AtomicInteger firstIndex = new AtomicInteger(0);
        AtomicInteger secondIndex = new AtomicInteger(0);
        int firstPower = BitSetUtil.getIntBySize(firstBit, firstIndex, 3);
        int secondPower = BitSetUtil.getIntBySize(secondBit, secondIndex, 3);

        double content = firstPower >= secondPower ? BitSetUtil.getDouble(firstBit, firstIndex) : BitSetUtil.getDouble(secondBit, secondIndex);
        result += content;

        // 共有13个维度
        for (int i = 0; i < 13; i++) {
            firstPower = BitSetUtil.getIntBySize(firstBit, firstIndex, 3);
            secondPower = BitSetUtil.getIntBySize(secondBit, secondIndex, 3);
            double firstResult = calcDimensionResult(firstBit, firstIndex, param[i]);
            double secondResult = calcDimensionResult(secondBit, secondIndex, param[i]);
            result += firstPower >= secondPower ? firstResult : secondResult;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        BitSet firstBit = firstDna();
        BitSet secondBit = secondDna();
        AtomicInteger firstIndex = new AtomicInteger(0);
        AtomicInteger secondIndex = new AtomicInteger(0);
        int firstPower = BitSetUtil.getIntBySize(firstBit, firstIndex, 3);
        int secondPower = BitSetUtil.getIntBySize(secondBit, secondIndex, 3);

        double content = firstPower >= secondPower ? BitSetUtil.getDouble(firstBit, firstIndex, 4) : BitSetUtil.getDouble(secondBit, secondIndex, 4);
        result.append(new BigDecimal(Double.toString(content)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());

        char name = 'a';
        // 共有13个维度
        for (int i = 0; i < 13; i++) {
            firstPower = BitSetUtil.getIntBySize(firstBit, firstIndex, 3);
            secondPower = BitSetUtil.getIntBySize(secondBit, secondIndex, 3);
            String firstResult = calcDimensionStr(firstBit, firstIndex, name);
            String secondResult = calcDimensionStr(secondBit, secondIndex, name);
            String s = firstPower >= secondPower ? firstResult : secondResult;
            if (StringUtil.isNotEmpty(s)) {
                if (!s.startsWith("-")) {
                    result.append("+");
                }
                result.append(s);
            }
            name += 1;
        }
        return result.toString();
    }

    @Override
    protected Individual<Double[], Double> makeNewIndividual(BitSet firstDna, BitSet secondDna, int size) {
        return new TestHeartIndividual(firstDna, secondDna, size);
    }

    private String calcDimensionStr(BitSet changeBit, AtomicInteger changeIndex, char name) {
        StringBuilder result = new StringBuilder();
        int itemSize = BitSetUtil.getIntBySize(changeBit, changeIndex, 8) % 4;
        boolean init = true;
        for (int i = 0; i < itemSize; i++) {
            String itemResult = calcItemStr(changeBit, changeIndex, name);
            if (StringUtil.isEmpty(itemResult)) {
                continue;
            }

            if (init) {
                init = false;
            } else if (!itemResult.startsWith("-")) {
                result.append("+");
            }

            result.append(itemResult);
        }

        return result.toString();
    }

    private String calcItemStr(BitSet changeBit, AtomicInteger changeIndex, char name) {
        StringBuilder result = new StringBuilder();

        int functionCode = BitSetUtil.getIntBySize(changeBit, changeIndex, 8) % MathFunction.size();
        double itemCoeff = BitSetUtil.getDouble(changeBit, changeIndex, 4);
        double d = new BigDecimal(Double.toString(itemCoeff)).setScale(4, RoundingMode.HALF_UP).doubleValue();
        if (d == 0) {
            return "";
        }
        result.append(d);
        result.append("*");
        MathFunction byCode = MathFunction.getByCode(functionCode);
        List<Pair<Integer, Class<? extends Number>>> classBitCountAndType = byCode.getClassBitCountAndType();

        Number[] params = new Number[classBitCountAndType.size()];
        for (int i = 0; i < classBitCountAndType.size(); i++) {
            Pair<Integer, Class<? extends Number>> integerClassPair = classBitCountAndType.get(i);
            Class<? extends Number> value = integerClassPair.getValue();
            if (Objects.equals(value, Integer.class)) {
                params[i] = BitSetUtil.getIntBySize(changeBit, changeIndex, integerClassPair.getKey());
            } else if (Objects.equals(value, Long.class)) {
                params[i] = BitSetUtil.getLongBySize(changeBit, changeIndex, integerClassPair.getKey());
            } else if (Objects.equals(value, Double.class)) {
                params[i] = BitSetUtil.getDoubleBySize(changeBit, changeIndex, integerClassPair.getKey(), 4);
            }
        }
        result.append(byCode.makeString(name, params));
        return result.toString();
    }

    /**
     * 计算一个维度所有项的结果
     *
     * @param changeBit   dna
     * @param changeIndex dna开始下标
     * @param x
     */
    private double calcDimensionResult(BitSet changeBit, AtomicInteger changeIndex, Double x) {
        double result = 0;
        int itemSize = BitSetUtil.getIntBySize(changeBit, changeIndex, 8) % 3;
        for (int i = 0; i < itemSize; i++) {
            double itemResult = calcItemResult(changeBit, changeIndex, x);
            if (Objects.equals(Double.NaN, itemResult)) {
                continue;
            }
            result += itemResult;
        }

        return result;
    }

    /**
     * 计算一个维度内所有项
     *
     * @param changeBit   dna
     * @param changeIndex dna开始下标
     * @param x
     *
     * @return
     */
    private double calcItemResult(BitSet changeBit, AtomicInteger changeIndex, Double x) {
        int functionCode = BitSetUtil.getIntBySize(changeBit, changeIndex, 8) % MathFunction.size();
        double itemCoeff = BitSetUtil.getDouble(changeBit, changeIndex, 4);

        MathFunction byCode = MathFunction.getByCode(functionCode);
        List<Pair<Integer, Class<? extends Number>>> classBitCountAndType = byCode.getClassBitCountAndType();

        Number[] params = new Number[classBitCountAndType.size()];
        for (int i = 0; i < classBitCountAndType.size(); i++) {
            Pair<Integer, Class<? extends Number>> integerClassPair = classBitCountAndType.get(i);
            Class<? extends Number> value = integerClassPair.getValue();
            if (Objects.equals(value, Integer.class)) {
                params[i] = BitSetUtil.getIntBySize(changeBit, changeIndex, integerClassPair.getKey());
            } else if (Objects.equals(value, Long.class)) {
                params[i] = BitSetUtil.getLongBySize(changeBit, changeIndex, integerClassPair.getKey());
            } else if (Objects.equals(value, Double.class)) {
                params[i] = BitSetUtil.getDoubleBySize(changeBit, changeIndex, integerClassPair.getKey());
            }
        }
        return itemCoeff * byCode.run(x, params);
    }
}
