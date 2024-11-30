package top.uhyils.usher.genetic.core.individual;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import org.apache.commons.lang3.RandomUtils;
import top.uhyils.usher.genetic.core.dna.Dna;
import top.uhyils.usher.util.DnaUtil;
import top.uhyils.usher.util.Pair;

/**
 * 个体模板
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月15日 16时35分
 */
public abstract class AbstractIndividual<T, E> implements Individual<T, E> {

    private static Random RANDOM = new Random();
    //
    //    /**
    //     * 第一条dna
    //     */
    //    protected final Dna firstDna;
    //
    //    /**
    //     * 第二条dna
    //     */
    //    protected final Dna secondDna;


    protected final Dna firstDna;

    protected final Dna secondDna;

    /**
     * 在dna没有变的前提下缓存结果
     */
    private final Map<T, E> cacheResult = new HashMap<>();

    public AbstractIndividual(Dna firstDna, Dna secondDna) {
        this.firstDna = new Dna(firstDna);
        this.secondDna = new Dna(secondDna);
    }

    public void clearCache() {
        cacheResult.clear();
    }

    @Override
    public Pair<Integer, Integer> getCoeff(int index) {
        Pair<Integer, Integer> firstDnaCoeff = firstDna.getCoeff(index);
        Pair<Integer, Integer> secondDnaCoeff = secondDna.getCoeff(index);
        if (firstDnaCoeff.getKey() >= secondDnaCoeff.getKey()) {
            return firstDnaCoeff;
        } else {
            return secondDnaCoeff;
        }
    }

    @Override
    public void setCoeff(int index, Integer power, Integer coeff) {
        Pair<Integer, Integer> firstDnaCoeff = firstDna.getCoeff(index);
        Pair<Integer, Integer> secondDnaCoeff = secondDna.getCoeff(index);
        if (firstDnaCoeff.getKey() >= secondDnaCoeff.getKey()) {
            firstDna.setCoeff(index, power, coeff);
        } else {
            secondDna.setCoeff(index, power, coeff);
        }
    }

    @Override
    public Individual<T, E> cross(Individual<T, E> individual) {
        return makeNewIndividual(firstDna(), individual.secondDna());
    }

    @Override
    public void variation(long virusDna) {
        // 注 changeOrder: 顺序改变 changeDirect: 直接改变 changeByVirus: 外来数据改变 changeAll: 完全改变
        int random = RANDOM.nextInt(10);
        if (virusDna == 0) {
            if (random < 4) {
                changeOrder(firstDna(), secondDna());
            } else if (random < 8) {
                changeDirect(firstDna(), secondDna());
            } else {
                changeAll();
            }
        } else {
            if (random < 2) {
                changeOrder(firstDna(), secondDna());
            } else if (random < 4) {
                changeDirect(firstDna(), secondDna());
            } else if (random < 8) {
                changeByVirus(firstDna(), secondDna(), virusDna);
            } else {
                changeAll();
            }
        }
        cacheResult.clear();
    }

    @Override
    public Dna firstDna() {
        return firstDna;
    }

    @Override
    public Dna secondDna() {
        return secondDna;
    }

    @Override
    public String toString() {
        return firstDna() + ":" + secondDna();
    }

    @Override
    public int size() {
        return Math.max(firstDna.size(), secondDna.size());
    }

    @Override
    public void directionalLearn(T param, E realResult, Double learningRate) {
        // 这里用doFindResult 不用findResult 因为需要一直改维度 类似于变异,需要清空,所以在里面不用findResult
        E result = doFindResult(param);
        dealDiff(param, result, realResult, learningRate);
    }

    @Override
    public E findResult(T param) {
        if (cacheResult.containsKey(param)) {
            return cacheResult.get(param);
        }
        E result = doFindResult(param);
        cacheResult.put(param, result);
        return result;
    }

    /**
     * 混合遗传算法引入梯度下降思想, 处理差值(反向学习)
     *
     * @param params       参数
     * @param result       计算值
     * @param targetResult 真实值
     * @param learningRate 学习率
     */
    protected abstract void dealDiff(T params, E result, E targetResult, Double learningRate);

    /**
     * 创建一个真正的个体
     *
     * @param firstDna  第一条dna
     * @param secondDna 第二条dna
     *
     * @return
     */
    protected abstract Individual<T, E> makeNewIndividual(Dna firstDna, Dna secondDna);

    /**
     * 获取结果
     *
     * @param param
     *
     * @return
     */
    protected abstract E doFindResult(T param);

    private void changeAll() {
        changeAll(firstDna());
        changeAll(secondDna());
    }

    private void changeAll(Dna dna) {
        int bigSize = 100;
        long[] fragment = new long[bigSize];
        for (int j = 0; j < bigSize; j++) {
            fragment[j] = RandomUtils.nextLong(0, Long.MAX_VALUE);
        }
        dna.reSet(fragment);
    }

    /**
     * 根据外来数据改变
     */
    private void changeByVirus(Dna firstDna, Dna secondDna, long virusDna) {
        Dna changeDna;
        if (Objects.equals(RANDOM.nextInt(2), 1)) {
            changeDna = firstDna;
        } else {
            changeDna = secondDna;
        }
        Dna virusDnaBit = Dna.valueOf(new long[]{virusDna}, changeDna.getPowerBitSize(), changeDna.getParamBitSize());

        int index = RANDOM.nextInt(size());
        DnaUtil.swap(changeDna, index, virusDnaBit, 0, virusDnaBit.size());
    }

    /**
     * 直接改变
     */
    private void changeDirect(Dna firstDna, Dna secondDna) {
        Dna changeDna;
        if (Objects.equals(RANDOM.nextInt(2), 1)) {
            changeDna = firstDna;
        } else {
            changeDna = secondDna;
        }
        int size = RANDOM.nextInt(size());
        int index = RANDOM.nextInt(size() - size);
        DnaUtil.negation(changeDna, index, size);
    }

    /**
     * 顺序改变
     */
    private void changeOrder(Dna firstDna, Dna secondDna) {
        int thisSize = size();
        int size = RANDOM.nextInt(thisSize);
        int firstIndex = RANDOM.nextInt(thisSize - size);
        int secondIndex = RANDOM.nextInt(thisSize - size);

        // 判断两个dna修改 还是同一个dna修改
        if (Objects.equals(RANDOM.nextInt(2), 1)) {
            // 两个dna修改
            DnaUtil.swap(firstDna, firstIndex, secondDna, secondIndex, size);
            return;
        }

        int dnaIndex = RANDOM.nextInt(2);
        Dna changeDna;
        if (Objects.equals(dnaIndex, 1)) {
            changeDna = firstDna;
        } else {
            changeDna = secondDna;
        }
        DnaUtil.swap(changeDna, firstIndex, changeDna, secondIndex, size);
    }
}
