package top.uhyils.usher.genetic.core.population;

import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.RandomUtils;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.genetic.core.dna.Dna;
import top.uhyils.usher.genetic.core.individual.Individual;
import top.uhyils.usher.genetic.core.individual.SigmoidIndividual;

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
    private static Dna randomDna() {
        int bigSize = 100;
        long[] fragment = new long[bigSize];
        for (int j = 0; j < bigSize; j++) {
            fragment[j] = RandomUtils.nextLong(0, Long.MAX_VALUE);
        }
        return Dna.valueOf(fragment, 3, 11);
    }

    @Override
    protected Individual<Double[], Double> makeNewIndividual() {
        Dna firstDna = firstDna();
        Dna secondDna = secondDna();
        return new SigmoidIndividual(firstDna, secondDna, dimLength);
    }

    @Override
    protected Double dealResults(List<Double> results) {
        return results.stream().mapToDouble(t -> t).average().getAsDouble();
    }


    @NotNull
    private Dna secondDna() {
        return randomDna();
    }

    @NotNull
    private Dna firstDna() {
        return randomDna();
    }
}
