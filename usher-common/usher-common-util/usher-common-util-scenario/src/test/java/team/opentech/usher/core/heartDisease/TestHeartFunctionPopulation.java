package team.opentech.usher.core.heartDisease;

import java.util.List;
import java.util.Properties;
import team.opentech.usher.FitnessHandler;
import team.opentech.usher.Individual;
import team.opentech.usher.core.AbstractPopulation;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月27日 14时59分
 */
public class TestHeartFunctionPopulation extends AbstractPopulation<Double[], Double> {

    public TestHeartFunctionPopulation(Properties config, FitnessHandler<Double[], Double> fitnessHandler) {
        super(config, fitnessHandler);
    }

    public TestHeartFunctionPopulation(Integer k, Double initPercentage, Double crossPercentage, Double variationPercentage, FitnessHandler<Double[], Double> fitnessHandler) {
        super(k, initPercentage, crossPercentage, variationPercentage, fitnessHandler);
    }

    @Override
    protected Individual<Double[], Double> makeNewIndividual() {
        // todo 如何生成一个随机的新的个体
        //        return new TestHeartIndividual(firstDna(), secondDna());
        return null;
    }

    @Override
    protected Double dealResults(List<Double> results) {
        return results.stream().mapToDouble(t -> t).average().getAsDouble();
    }
}
