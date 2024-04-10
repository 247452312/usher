package team.opentech.usher.core.heartDisease;

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
        // 输出使用激活函数
        return 1.0 / (1 + Math.pow(Math.E, -result));
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

        // 共有13个维度
        for (int i = 0; i < 13; i++) {
            firstPower = BitSetUtil.getIntBySize(firstBit, firstIndex, 3);
            secondPower = BitSetUtil.getIntBySize(secondBit, secondIndex, 3);
            String s;
            if (firstPower >= secondPower) {
                s = calcDimensionStr(firstBit, firstIndex, "x" + i);
                secondIndex.set(firstIndex.get());
            } else {
                s = calcDimensionStr(secondBit, secondIndex, "x" + i);
                firstIndex.set(secondIndex.get());
            }
            if (StringUtil.isNotEmpty(s)) {
                if (!s.startsWith("-")) {
                    result.append("+");
                }
                result.append(s);
            }
        }
        return result.toString();
    }

    @Override
    protected void dealDiff(Double result, Double targetResult, Double learningRate) {
        // 二分神经网络损失函数为 logistic回归函数 f(x) = -y * log(h(x)) - (1-y) * log(1-h(x)) 其中 y为实际值 h(x)为计算值
        // 损失函数对每个维度求偏导,得出的偏导为 f'(x) = (h(x) - y) * g`(x)  其中 g`(x) 为sigmoid之前公式对维度求偏导得来
        // 因为我们知道 sigmoid之前公式为  f(x1,x2,...) = ax1 + bx2 + ....    所以 f 对x求偏导的结果为 a 对x2求偏导的结果为b  因此推出 f 对每一个维度求偏导均得出结果为维度对应的系数
        BitSet firstBit = firstDna();
        BitSet secondBit = secondDna();
        AtomicInteger firstIndex = new AtomicInteger(0);
        AtomicInteger secondIndex = new AtomicInteger(0);
        double diff = result - targetResult;

        // 共13个维度
        for (int i = 0; i < 13; i++) {
            int firstPower = BitSetUtil.getIntBySize(firstBit, firstIndex, 3);
            int secondPower = BitSetUtil.getIntBySize(secondBit, secondIndex, 3);
            AtomicInteger changeIndex;
            AtomicInteger otherIndex;
            BitSet changeBit;
            if (firstPower >= secondPower) {
                changeIndex = firstIndex;
                otherIndex = secondIndex;
                changeBit = firstBit;
            } else {
                changeIndex = secondIndex;
                otherIndex = firstIndex;
                changeBit = secondBit;
            }
            int startIndex = changeIndex.get();
            float coeff = findFloat(changeBit, changeIndex);
            otherIndex.set(changeIndex.get());
            double targetChangeNum = diff * coeff * learningRate;
            changeFloat(changeBit, startIndex, targetChangeNum);
        }
    }

    @Override
    protected Individual<Double[], Double> makeNewIndividual(BitSet firstDna, BitSet secondDna, int size) {
        return new TestHeartIndividual(firstDna, secondDna, size);
    }

    /**
     * 将一个小数放置到bitSet中去
     *
     * @param changeBit       待修改的dna
     * @param startIndex      起始下标
     * @param targetChangeNum 要修改成的值
     */
    private void changeFloat(BitSet changeBit, int startIndex, double targetChangeNum) {
        // 1.修改符号位
        changeBit.set(startIndex++, targetChangeNum >= 0);
        // 2.修改整数位值
        BitSetUtil.setIntBySize(changeBit, startIndex, 5, (int) targetChangeNum);
        startIndex += 5;
        // 3.修改小数位值
        BitSetUtil.setIntBySize(changeBit, startIndex, 5, (int) ((targetChangeNum - (int) targetChangeNum) * 100));
    }

    private String calcDimensionStr(BitSet changeBit, AtomicInteger changeIndex, String name) {
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

        return "1/(1+e^-" + result + ")";
    }

    private String calcItemStr(BitSet changeBit, AtomicInteger changeIndex, String name) {
        StringBuilder result = new StringBuilder();
        float aFloat = findFloat(changeBit, changeIndex);
        result.append(aFloat);
        result.append("*");
        result.append(name);

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
        return aFloat * x;
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
