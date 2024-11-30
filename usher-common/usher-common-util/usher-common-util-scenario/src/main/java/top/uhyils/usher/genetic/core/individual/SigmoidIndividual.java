package top.uhyils.usher.genetic.core.individual;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import top.uhyils.usher.genetic.core.dna.Dna;
import top.uhyils.usher.util.Pair;
import top.uhyils.usher.util.StringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月27日 15时19分
 */
public class SigmoidIndividual extends AbstractIndividual<Double[], Double> {

    /**
     * 维度
     */
    private final int dimLength;


    public SigmoidIndividual(Dna firstDna, Dna secondDna, int dimLength) {
        super(firstDna, secondDna);
        this.dimLength = dimLength;
    }

    /**
     * 将一个小数放置到Dna中去
     *
     * @param changeBit       待修改的dna
     * @param startIndex      起始下标
     * @param targetChangeNum 要修改成的值
     */
    protected static void changeFloat(Dna changeBit, int startIndex, float targetChangeNum) {
        int signed;
        if (targetChangeNum >= 0) {
            // 1.修改符号位
            signed = 1;
        } else {
            signed = 0;
            targetChangeNum = -targetChangeNum;
        }

        int intValue = (int) targetChangeNum;
        int floatValue = ((int) (targetChangeNum * 100 - (int) targetChangeNum * 100) & 0b11111) << 5;
        int value = (signed << 10) + intValue + floatValue;
        changeBit.setParam(startIndex, value);
    }

    protected static float findFloat(Integer bit) {
        int intNum = bit & ((1 << 5) - 1);
        bit >>= 5;

        float v = (bit & ((1 << 5) - 1)) / 100f;
        bit >>= 5;

        int sign = bit & 0b1;
        float decimalNum = (float) (v - Math.floor(v));
        float v1 = intNum + decimalNum;
        if (sign == 0) {
            v1 = -v1;
        }
        return v1;

    }

    public int getDimLength() {
        return dimLength;
    }

    @Override
    public Double doFindResult(Double[] param) {
        double result = 0;
        Dna firstDna = firstDna();
        Dna secondDna = secondDna();
        AtomicInteger firstIndex = new AtomicInteger(0);
        AtomicInteger secondIndex = new AtomicInteger(0);
        Pair<Integer, Integer> first = firstDna.getCoeff(firstIndex.getAndIncrement());
        Pair<Integer, Integer> second = secondDna.getCoeff(secondIndex.getAndIncrement());

        double content = first.getKey() >= second.getKey() ? findFloat(first.getValue()) : findFloat(second.getValue());
        result += content;

        // 共有dimLength个维度
        for (int i = 0; i < dimLength; i++) {
            first = firstDna.getCoeff(firstIndex.getAndIncrement());
            second = secondDna.getCoeff(secondIndex.getAndIncrement());
            result += first.getKey() >= second.getKey() ? findFloat(first.getValue()) * param[i] : findFloat(second.getValue()) * param[i];
        }
        // 输出使用激活函数
        return 1.0 / (1 + Math.pow(Math.E, -result));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        Dna firstDna = firstDna();
        Dna secondDna = secondDna();
        AtomicInteger firstIndex = new AtomicInteger(0);
        AtomicInteger secondIndex = new AtomicInteger(0);
        Pair<Integer, Integer> first = firstDna.getCoeff(firstIndex.getAndIncrement());
        Pair<Integer, Integer> second = secondDna.getCoeff(secondIndex.getAndIncrement());
        double content = first.getKey() >= second.getKey() ? findFloat(first.getValue()) : findFloat(second.getValue());
        if (!Objects.equals(content, 0)) {
            result.append(content);
        }

        // 共有dimLength个维度
        for (int i = 0; i < dimLength; i++) {

            first = firstDna.getCoeff(firstIndex.getAndIncrement());
            second = secondDna.getCoeff(secondIndex.getAndIncrement());

            String s = first.getKey() >= second.getKey() ? calcDimensionStr(first.getValue(), "x" + i) : calcDimensionStr(second.getValue(), "x" + i);
            if (StringUtil.isNotEmpty(s)) {
                if (!s.startsWith("-")) {
                    result.append("+");
                }
                result.append(s);
                result.append(" ");
            }
        }
        return result.toString();
    }

    @Override
    protected void dealDiff(Double[] params, Double result, Double targetResult, Double learningRate) {
        // 二分神经网络损失函数为 logistic回归函数 f(x) = -y * log(h(x)) - (1-y) * log(1-h(x)) 其中 y为实际值 h(x)为计算值
        // 损失函数对每个维度求偏导,得出的偏导为 f'(x) = (h(x) - y) * g`(x)  其中 g`(x) 为sigmoid之前公式对维度求偏导得来
        // 因为我们知道 sigmoid之前公式为  f(x1,x2,...) = ax1 + bx2 + ....    所以 f 对x求偏导的结果为 a 对x2求偏导的结果为b  因此推出 f 对每一个维度求偏导均得出结果为维度对应的系数
        // 常量为: ( h(x) - y ) * 1

        // h(x) - y
        double diff = result - targetResult;

        Dna firstDna = firstDna();
        Dna secondDna = secondDna();
        AtomicInteger firstIndex = new AtomicInteger(0);
        AtomicInteger secondIndex = new AtomicInteger(0);
        Pair<Integer, Integer> first = firstDna.getCoeff(firstIndex.getAndIncrement());
        Pair<Integer, Integer> second = secondDna.getCoeff(secondIndex.getAndIncrement());

        if (first.getKey() >= second.getKey()) {
            float content = findFloat(first.getValue());
            changeFloat(firstDna, 0, (float) (content - diff * learningRate));
        } else {
            float content = findFloat(second.getValue());
            changeFloat(secondDna, 0, (float) (content - diff * learningRate));
        }

        // 共dimLength个维度
        for (int i = 0; i < dimLength; i++) {
            int firstIndexNum = firstIndex.getAndIncrement();
            first = firstDna.getCoeff(firstIndexNum);
            int secondIndexNum = secondIndex.getAndIncrement();
            second = secondDna.getCoeff(secondIndexNum);
            if (first.getKey() >= second.getKey()) {
                float coeff = findFloat(first.getValue());
                changeFloat(firstDna, firstIndexNum, (float) (coeff - diff * params[i] * learningRate));
            } else {
                float coeff = findFloat(second.getValue());
                changeFloat(secondDna, secondIndexNum, (float) (coeff - diff * params[i] * learningRate));
            }
        }
    }

    @Override
    protected Individual<Double[], Double> makeNewIndividual(Dna firstDna, Dna secondDna) {
        return new SigmoidIndividual(firstDna, secondDna, dimLength);
    }

    private String calcDimensionStr(Integer value, String name) {
        float aFloat = findFloat(value);
        if (Objects.equals(aFloat, 0)) {
            return null;
        }
        return aFloat + "*" + name;
    }
}
