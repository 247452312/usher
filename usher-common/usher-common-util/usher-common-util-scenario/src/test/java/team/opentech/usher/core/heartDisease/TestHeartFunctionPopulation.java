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
public class TestHeartFunctionPopulation extends AbstractPopulation<Float[], Float> {

    public TestHeartFunctionPopulation(Properties config, FitnessHandler<Float[], Float> fitnessHandler) {
        super(config, fitnessHandler);
    }

    public TestHeartFunctionPopulation(Integer k, Float initPercentage, Float crossPercentage, Float variationPercentage, FitnessHandler<Float[], Float> fitnessHandler) {
        super(k, initPercentage, crossPercentage, variationPercentage, fitnessHandler);
    }

    @Override
    protected Individual<Float[], Float> makeNewIndividual() {
        // todo 如何生成一个随机的新的个体
        //        return new TestHeartIndividual(firstDna(), secondDna());
        return null;
    }

    @Override
    protected Float dealResults(List<Float> results) {
        return (float) results.stream().mapToDouble(t -> t).average().getAsDouble();
    }
}
