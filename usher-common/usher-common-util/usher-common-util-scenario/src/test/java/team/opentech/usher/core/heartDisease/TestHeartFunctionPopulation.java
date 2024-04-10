package team.opentech.usher.core.heartDisease;

import java.util.BitSet;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.RandomUtils;
import team.opentech.usher.FitnessHandler;
import team.opentech.usher.Individual;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.core.AbstractPopulation;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月27日 14时59分
 */
public class TestHeartFunctionPopulation extends AbstractPopulation<Double[], Double> {

    public TestHeartFunctionPopulation(Properties config, FitnessHandler<Double[], Double> fitnessHandler) {
        super(config, fitnessHandler);
    }

    /**
     * 随机生成一个DNA
     *
     * @return
     */
    @NotNull
    private static BitSet randomDna() {
        long[] longs = new long[200];
        for (int i = 0; i < 200; i++) {
            longs[i] = RandomUtils.nextLong(0, Long.MAX_VALUE);
        }
        return BitSet.valueOf(longs);
    }

    @Override
    protected Individual<Double[], Double> makeNewIndividual() {
        BitSet firstDna = firstDna();
        BitSet secondDna = secondDna();
        return new TestHeartIndividual(firstDna, secondDna, Math.max(firstDna.size(), secondDna.size()));
    }

    @Override
    protected Double dealResults(List<Double> results) {
        return results.stream().mapToDouble(t -> t).average().getAsDouble();
    }


    @NotNull
    private BitSet secondDna() {
        return randomDna();
    }

    @NotNull
    private BitSet firstDna() {
        return randomDna();
    }
}
