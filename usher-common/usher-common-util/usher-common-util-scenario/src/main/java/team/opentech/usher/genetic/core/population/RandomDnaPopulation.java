package team.opentech.usher.genetic.core.population;

import java.util.BitSet;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.RandomUtils;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.genetic.core.individual.Individual;
import team.opentech.usher.genetic.core.individual.SigmoidIndividual;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月27日 14时59分
 */
public class RandomDnaPopulation extends AbstractPopulation<Double[], Double> {

    /**
     * 维度
     */
    private final int dimLength;

    public RandomDnaPopulation(Properties config, int dimLength) {
        super(config);
        this.dimLength = dimLength;
    }

    public RandomDnaPopulation(Properties config, List<Individual<Double[], Double>> individuals) {
        super(config, individuals);
        SigmoidIndividual sigmoidIndividual = (SigmoidIndividual) individuals.get(0);
        this.dimLength = sigmoidIndividual.getDimLength();
    }

    /**
     * 随机生成一个DNA
     *
     * @return
     */
    @NotNull
    private static BitSet randomDna() {
        long[] longs = new long[16];
        for (int i = 0; i < 16; i++) {
            longs[i] = RandomUtils.nextLong(0, Long.MAX_VALUE);
        }
        return BitSet.valueOf(longs);
    }

    @Override
    protected Individual<Double[], Double> makeNewIndividual() {
        BitSet firstDna = firstDna();
        BitSet secondDna = secondDna();
        return new SigmoidIndividual(firstDna, secondDna, Math.max(firstDna.size(), secondDna.size()), dimLength);
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
