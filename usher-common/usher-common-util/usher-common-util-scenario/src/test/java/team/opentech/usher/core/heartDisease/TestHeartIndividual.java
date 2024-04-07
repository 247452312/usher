package team.opentech.usher.core.heartDisease;

import java.math.BigDecimal;
import java.util.BitSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import team.opentech.usher.Individual;
import team.opentech.usher.core.AbstractIndividual;
import team.opentech.usher.util.BitSetUtil;
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

        double content = firstPower >= secondPower ? findFloat(firstBit, firstIndex) : findFloat(secondBit, secondIndex);
        result += content;

        // 共有13个维度
        for (int i = 0; i < 13; i++) {
            firstPower = BitSetUtil.getIntBySize(firstBit, firstIndex, 3);
            secondPower = BitSetUtil.getIntBySize(secondBit, secondIndex, 3);
            if (firstPower >= secondPower) {
                result += calcDimensionResult(firstBit, firstIndex, param[i]);
                secondIndex.set(firstIndex.get());
            } else {
                result += calcDimensionResult(secondBit, secondIndex, param[i]);
                firstIndex.set(secondIndex.get());
            }
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
        double content = firstPower >= secondPower ? findFloat(firstBit, firstIndex) : findFloat(secondBit, secondIndex);
        result.append(content);

        char name = 'a';
        // 共有13个维度
        for (int i = 0; i < 13; i++) {
            firstPower = BitSetUtil.getIntBySize(firstBit, firstIndex, 3);
            secondPower = BitSetUtil.getIntBySize(secondBit, secondIndex, 3);
            String s;
            if (firstPower >= secondPower) {
                s = calcDimensionStr(firstBit, firstIndex);
                secondIndex.set(firstIndex.get());
            } else {
                s = calcDimensionStr(secondBit, secondIndex);
                firstIndex.set(secondIndex.get());
            }
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

    private String calcDimensionStr(BitSet changeBit, AtomicInteger changeIndex) {
        StringBuilder result = new StringBuilder();
        int itemSize = BitSetUtil.getIntBySize(changeBit, changeIndex, 8) % 4;
        boolean init = true;
        for (int i = 0; i < itemSize; i++) {
            String itemResult = calcItemStr(changeBit, changeIndex);
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

    private String calcItemStr(BitSet changeBit, AtomicInteger changeIndex) {
        StringBuilder result = new StringBuilder();
        float aFloat = findFloat(changeBit, changeIndex);
        float bFloat = findFloat(changeBit, changeIndex);
        result.append(aFloat);
        result.append("*x");
        if (bFloat > 0) {
            result.append("+");
        }
        result.append(bFloat);
        result.append("*x^2");

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
        float aFloat = findFloat(changeBit, changeIndex);
        float bFloat = findFloat(changeBit, changeIndex);
        return aFloat * x + bFloat * x * x;
    }

    private float findFloat(BitSet changeBit, AtomicInteger changeIndex) {
        boolean b = changeBit.get(changeIndex.getAndIncrement());
        int intNum = BitSetUtil.getIntBySize(changeBit, changeIndex, 5);
        float v = BitSetUtil.getIntBySize(changeBit, changeIndex, 5) / 100f;
        float decimalNum = (float) (v - Math.floor(v));
        float v1 = intNum + decimalNum;
        if (!b) {
            v1 = -v1;
        }
        return v1;

    }
}
