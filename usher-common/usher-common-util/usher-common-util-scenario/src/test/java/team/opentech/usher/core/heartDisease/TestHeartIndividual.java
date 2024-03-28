package team.opentech.usher.core.heartDisease;

import team.opentech.usher.Individual;
import team.opentech.usher.core.AbstractIndividual;
import team.opentech.usher.lang.LongByte;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月27日 15时19分
 */
public class TestHeartIndividual extends AbstractIndividual<Float[], Float> {

    protected TestHeartIndividual(int size) {
        super(size);
    }

    public TestHeartIndividual(LongByte firstDna, LongByte secondDna) {
        super(firstDna, secondDna);
    }

    @Override
    public Float findResult(Float[] param) {
        //        return asd;
        // todo 如何计算结果
        return null;
    }

    @Override
    protected Individual<Float[], Float> makeNewIndividual(LongByte firstDna, LongByte secondDna) {
        return new TestHeartIndividual(firstDna, secondDna);
    }
}
