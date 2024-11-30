package top.uhyils.usher.genetic.core.dna;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月19日 09时56分
 */
class DnaTest {


    @Test
    void setPower() {
        Dna dna = Dna.valueOf(new long[]{RandomUtils.nextLong(0, Long.MAX_VALUE)}, 3, 11);
        dna.setPower(1, 3);
        Integer key = dna.getCoeff(1).getKey();
        assert key == 3;
    }

    @Test
    void setParam() {
        Dna dna = Dna.valueOf(new long[]{RandomUtils.nextLong(0, Long.MAX_VALUE)}, 3, 11);
        dna.setParam(1, 0b10001100010);
        Integer value = dna.getCoeff(1).getValue();
        assert value == 0b10001100010;
    }

    @Test
    void getCoeff() {
        Dna dna = Dna.valueOf(new long[]{(1L << (14 * 3)) - 1}, 3, 11);
        Pair<Integer, Integer> coeff = dna.getCoeff(1);
        assert coeff.getKey() == 0b111;
        assert coeff.getValue() == (1 << 11) - 1;
    }


    @Test
    void size() {
        Dna dna = Dna.valueOf(new long[]{(1L << (14 * 3)) - 1}, 3, 11);
        Integer size = dna.size();
        assert size == 3;
    }


    @Test
    void negation() {
        Dna dna = Dna.valueOf(new long[]{(1L << (14 * 3)) - 1}, 3, 11);
        Pair<Integer, Integer> coeff = dna.getCoeff(1);
        assert coeff.getKey() == (1 << 3) - 1;
        assert coeff.getValue() == (1 << 11) - 1;
        dna.negation(1);
        Pair<Integer, Integer> coeff1 = dna.getCoeff(1);
        assert coeff1.getKey() == 0;
        assert coeff1.getValue() == 0;
    }
}
