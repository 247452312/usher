package team.opentech.usher.core;

import java.util.Objects;
import java.util.Random;
import team.opentech.usher.Individual;
import team.opentech.usher.lang.LongByte;
import team.opentech.usher.util.BytesUtil;
import team.opentech.usher.util.LongByteUtil;

/**
 * 个体模板
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月15日 16时35分
 */
public abstract class AbstractIndividual<T, E> implements Individual<T, E> {

    private static Random RANDOM = new Random();

    protected final LongByte firstDna;

    protected final LongByte secondDna;

    protected AbstractIndividual(int size) {
        this.firstDna = new LongByte(size);
        this.secondDna = new LongByte(size);
    }

    public AbstractIndividual(LongByte firstDna, LongByte secondDna) {
        this.firstDna = new LongByte(firstDna);
        this.secondDna = new LongByte(secondDna);
    }

    @Override
    public Individual<T, E> cross(Individual<T, E> individual) {
        return makeNewIndividual(firstDna(), individual.secondDna());
    }

    @Override
    public void variation(byte[] virusDna) {
        // 注
        int random = RANDOM.nextInt(10);
        if (virusDna == null || virusDna.length == 0) {
            if (random < 5) {
                changeOrder(firstDna(), secondDna());
            } else {
                changeDirect(firstDna(), secondDna());
            }
        } else {
            if (random < 2) {
                changeOrder(firstDna(), secondDna());
            } else if (random < 4) {
                changeDirect(firstDna(), secondDna());
            } else {
                changeByVirus(firstDna(), secondDna(), virusDna);
            }
        }
        // 顺序改变
        //        changeOrder(firstDna(), secondDna());
        // 直接改变
        //        changeDirect(firstDna(), secondDna());
        // 外来数据改变
        //        changeByVirus(firstDna(), secondDna(), virusDna);
    }

    @Override
    public LongByte firstDna() {
        return firstDna;
    }

    @Override
    public LongByte secondDna() {
        return secondDna;
    }

    /**
     * 创建一个真正的个体
     *
     * @param firstDna  第一条dna
     * @param secondDna 第二条dna
     *
     * @return
     */
    protected abstract Individual<T, E> makeNewIndividual(LongByte firstDna, LongByte secondDna);

    /**
     * 根据外来数据改变
     */
    private void changeByVirus(LongByte firstDna, LongByte secondDna, byte[] virusDna) {
        LongByte changeDna;
        if (Objects.equals(RANDOM.nextInt(2), 1)) {
            changeDna = firstDna;
        } else {
            changeDna = secondDna;
        }
        int index = RANDOM.nextInt(changeDna.byteSize());
        for (int i = 0; i < virusDna.length || index < changeDna.byteSize(); i++) {
            changeDna.setByte(index++, virusDna[i]);
        }
    }

    /**
     * 直接改变
     */
    private void changeDirect(LongByte firstDna, LongByte secondDna) {
        LongByte changeDna;
        if (Objects.equals(RANDOM.nextInt(2), 1)) {
            changeDna = firstDna;
        } else {
            changeDna = secondDna;
        }
        int size = RANDOM.nextInt(changeDna.byteSize());
        int index = RANDOM.nextInt(changeDna.byteSize() - size);
        changeDna.negation(index, size);
    }

    /**
     * 顺序改变
     */
    private void changeOrder(LongByte firstDna, LongByte secondDna) {
        int min = Math.min(firstDna.size(), secondDna.size());
        int size = RANDOM.nextInt(min);
        int firstIndex = RANDOM.nextInt(min - size);
        int secondIndex = RANDOM.nextInt(min - size);

        // 判断两个dna修改 还是同一个dna修改
        if (Objects.equals(RANDOM.nextInt(2), 1)) {
            // 两个dna修改
            LongByteUtil.swap(firstDna, firstIndex, secondDna, secondIndex, size);
            return;
        }

        int dnaIndex = RANDOM.nextInt(2);
        LongByte changeDna;
        if (Objects.equals(dnaIndex, 1)) {
            changeDna = firstDna;
        } else {
            changeDna = secondDna;
        }
        LongByteUtil.swap(changeDna, firstIndex, changeDna, secondIndex, size);
    }
}
