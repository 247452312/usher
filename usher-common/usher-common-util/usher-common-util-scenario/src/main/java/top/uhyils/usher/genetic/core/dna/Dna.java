package top.uhyils.usher.genetic.core.dna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import top.uhyils.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月19日 08时36分
 */
public class Dna implements Serializable {

    /**
     * 权值长度(二进制)
     */
    private final Integer powerBitSize;

    /**
     * 参数长度(二进制)
     */
    private final Integer paramBitSize;

    /**
     * dna的参数
     */
    private List<Integer> params;

    /**
     * dna的权值
     */
    private List<Integer> powers;

    public Dna(long[] randomSeed, Integer powerBitSize, Integer paramBitSize) {
        this.powerBitSize = powerBitSize;
        this.paramBitSize = paramBitSize;
        init(randomSeed);

    }

    public Dna(Dna other) {
        this.params = new ArrayList<>(other.params);
        this.powers = new ArrayList<>(other.powers);
        this.powerBitSize = other.powerBitSize;
        this.paramBitSize = other.paramBitSize;
    }


    public static Dna valueOf(long[] fragment, int powerBitSize, int paramBitSize) {
        return new Dna(fragment, powerBitSize, paramBitSize);
    }

    public void setPower(Integer index, Integer powerValue) {
        if (index >= size()) {
            return;
        }
        powers.set(index, powerValue);
    }

    public void setParam(Integer index, Integer paramValue) {
        if (index >= size()) {
            return;
        }
        params.set(index, paramValue);
    }

    public Pair<Integer, Integer> getCoeff(int index) {
        if (index >= size()) {
            return new Pair<>(0, 0);
        }
        return new Pair<>(powers.get(index), params.get(index));
    }

    public void reSet(long[] randomSeed) {
        init(randomSeed);
    }

    public void setCoeff(int index, Integer power, Integer coeff) {
        if (index >= size()) {
            return;
        }
        this.powers.set(index, power);
        this.params.set(index, coeff);
    }

    public Integer size() {
        return powers.size();
    }

    public Integer getPowerBitSize() {
        return powerBitSize;
    }

    public Integer getParamBitSize() {
        return paramBitSize;
    }

    public void negation(int i) {
        if (i >= size()) {
            return;
        }
        this.powers.set(i, (~this.powers.get(i) & ((1 << powerBitSize) - 1)));
        this.params.set(i, (~this.params.get(i) & ((1 << paramBitSize) - 1)));
    }

    private void init(long[] randomSeeds) {
        int capacity = Long.SIZE / (paramBitSize + powerBitSize);
        this.powers = new ArrayList<>(capacity * randomSeeds.length);
        this.params = new ArrayList<>(capacity * randomSeeds.length);
        for (long randomSeed : randomSeeds) {
            long findValueSeed = randomSeed;
            for (int i = 0; i < capacity; i++) {
                long power = findValueSeed & ((1L << powerBitSize) - 1);
                findValueSeed >>= powerBitSize;

                long param = findValueSeed & ((1L << paramBitSize) - 1);
                findValueSeed >>= paramBitSize;
                this.powers.add((int) power);
                this.params.add((int) param);
            }
        }


    }
}
