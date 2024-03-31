package team.opentech.usher.core.heartDisease;

import java.util.BitSet;
import team.opentech.usher.Individual;
import team.opentech.usher.core.AbstractIndividual;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月27日 15时19分
 */
public class TestHeartIndividual extends AbstractIndividual<Float[], Float> {

    protected TestHeartIndividual(int size) {
        super(size);
    }

    public TestHeartIndividual(BitSet firstDna, BitSet secondDna, int size) {
        super(firstDna, secondDna, size);
    }

    @Override
    public Float findResult(Float[] param) {
        //        return asd;
        // todo 如何计算结果
        return null;
    }

    @Override
    protected Individual<Float[], Float> makeNewIndividual(BitSet firstDna, BitSet secondDna, int size) {
        return new TestHeartIndividual(firstDna, secondDna, size);
    }
}
