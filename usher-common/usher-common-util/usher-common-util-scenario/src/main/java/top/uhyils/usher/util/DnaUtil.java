package top.uhyils.usher.util;

import top.uhyils.usher.genetic.core.dna.Dna;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月19日 08时58分
 */
public final class DnaUtil {

    private DnaUtil() {
        throw new RuntimeException("dna工具类不能初始化");
    }


    public static void swap(Dna changeDna, int changeIndex, Dna virusDnaBit, int virusIndex, Integer size) {
        for (int i = 0; i < size; i++) {
            Pair<Integer, Integer> coeff = changeDna.getCoeff(changeIndex + i);
            Pair<Integer, Integer> virus = virusDnaBit.getCoeff(virusIndex + i);
            changeDna.setCoeff(changeIndex + i, virus.getKey(), virus.getValue());
            virusDnaBit.setCoeff(virusIndex + i, coeff.getKey(), coeff.getValue());
        }
    }

    public static void negation(Dna changeDna, int index, int size) {
        for (int i = 0; i < size; i++) {
            changeDna.negation(index + i);
        }
    }
}
