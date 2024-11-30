package top.uhyils.usher.genetic.core.individual;

import org.junit.jupiter.api.Test;
import top.uhyils.usher.genetic.core.dna.Dna;
import top.uhyils.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月19日 10时25分
 */
class SigmoidIndividualTest {

    @Test
    void findFloatOrChangeFloat() {
        Dna dna = Dna.valueOf(new long[]{0b11111010110101}, 3, 11);
        Pair<Integer, Integer> coeff = dna.getCoeff(0);
        assert coeff.getKey() == 0b101;
        assert coeff.getValue() == 0b11111010110;
        float aFloat = SigmoidIndividual.findFloat(coeff.getValue());
        assert aFloat == 22.3f;
        SigmoidIndividual.changeFloat(dna, 0, aFloat + 1);
        coeff = dna.getCoeff(0);
        assert coeff.getKey() == 0b101;
        assert coeff.getValue() == 0b11111010111;
        aFloat = SigmoidIndividual.findFloat(coeff.getValue());
        assert aFloat == 23.3f;


    }

}
