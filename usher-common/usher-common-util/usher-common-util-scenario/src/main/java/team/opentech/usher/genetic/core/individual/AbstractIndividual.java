package team.opentech.usher.genetic.core.individual;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import org.apache.commons.lang3.RandomUtils;
import team.opentech.usher.util.BitSetUtil;

/**
 * 个体模板
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月15日 16时35分
 */
public abstract class AbstractIndividual<T, E> implements Individual<T, E> {

    private static Random RANDOM = new Random();

    /**
     * 第一条dna
     */
    protected final BitSet firstDna;

    /**
     * 第二条dna
     */
    protected final BitSet secondDna;

    /**
     * 初始长度
     */
    protected final int size;

    /**
     * 在dna没有变的前提下缓存结果
     */
    private final Map<T, E> cacheResult = new HashMap<>();

    protected AbstractIndividual(int size) {
        this.firstDna = new BitSet(size);
        this.secondDna = new BitSet(size);
        this.size = size;
    }

    public AbstractIndividual(BitSet firstDna, BitSet secondDna) {
        this(firstDna, secondDna, Math.max(firstDna.size(), secondDna.size()));
    }

    public AbstractIndividual(BitSet firstDna, BitSet secondDna, int size) {
        this.firstDna = (BitSet) firstDna.clone();
        this.secondDna = (BitSet) secondDna.clone();
        this.size = size;
    }

    @Override
    public Individual<T, E> cross(Individual<T, E> individual) {
        return makeNewIndividual(firstDna(), individual.secondDna(), Math.max(size(), individual.size()));
    }

    @Override
    public void variation(byte[] virusDna) {
        // 注 changeOrder: 顺序改变 changeDirect: 直接改变 changeByVirus: 外来数据改变 changeAll: 完全改变
        int random = RANDOM.nextInt(10);
        if (virusDna == null || virusDna.length == 0) {
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
    public BitSet firstDna() {
        return firstDna;
    }

    @Override
    public BitSet secondDna() {
        return secondDna;
    }

    @Override
    public String toString() {
        return firstDna() + ":" + secondDna();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void directionalLearn(T param, E realResult, Double learningRate) {
        E result = findResult(param);
        dealDiff(result, realResult, learningRate);
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
     * @param result       计算值
     * @param targetResult 真实值
     * @param learningRate 学习率
     */
    protected abstract void dealDiff(E result, E targetResult, Double learningRate);

    /**
     * 创建一个真正的个体
     *
     * @param firstDna  第一条dna
     * @param secondDna 第二条dna
     * @param size      长度
     *
     * @return
     */
    protected abstract Individual<T, E> makeNewIndividual(BitSet firstDna, BitSet secondDna, int size);

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

    private void changeAll(BitSet bitSet) {
        int i = RandomUtils.nextInt(0, Integer.MAX_VALUE);
        int index = Integer.toBinaryString(i).length() - 1;
        while (i > 0) {
            bitSet.set(index--, (i & 1) == 1);
            i >>= 1;
        }
    }

    /**
     * 根据外来数据改变
     */
    private void changeByVirus(BitSet firstDna, BitSet secondDna, byte[] virusDna) {
        BitSet changeDna;
        if (Objects.equals(RANDOM.nextInt(2), 1)) {
            changeDna = firstDna;
        } else {
            changeDna = secondDna;
        }
        BitSet virusDnaBit = BitSet.valueOf(virusDna);

        int index = RANDOM.nextInt(size());
        BitSetUtil.swap(changeDna, index, virusDnaBit, 0, virusDnaBit.length());
    }

    /**
     * 直接改变
     */
    private void changeDirect(BitSet firstDna, BitSet secondDna) {
        BitSet changeDna;
        if (Objects.equals(RANDOM.nextInt(2), 1)) {
            changeDna = firstDna;
        } else {
            changeDna = secondDna;
        }
        int size = RANDOM.nextInt(size());
        int index = RANDOM.nextInt(size() - size);
        BitSetUtil.negation(changeDna, index, size);
    }

    /**
     * 顺序改变
     */
    private void changeOrder(BitSet firstDna, BitSet secondDna) {
        int thisSize = size();
        int size = RANDOM.nextInt(thisSize);
        int firstIndex = RANDOM.nextInt(thisSize - size);
        int secondIndex = RANDOM.nextInt(thisSize - size);

        // 判断两个dna修改 还是同一个dna修改
        if (Objects.equals(RANDOM.nextInt(2), 1)) {
            // 两个dna修改
            BitSetUtil.swap(firstDna, firstIndex, secondDna, secondIndex, size);
            return;
        }

        int dnaIndex = RANDOM.nextInt(2);
        BitSet changeDna;
        if (Objects.equals(dnaIndex, 1)) {
            changeDna = firstDna;
        } else {
            changeDna = secondDna;
        }
        BitSetUtil.swap(changeDna, firstIndex, changeDna, secondIndex, size);
    }
}
