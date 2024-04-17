package team.opentech.usher.genetic.core.twoDimensional;

import java.util.List;
import java.util.Properties;
import team.opentech.usher.genetic.core.population.AbstractPopulation;
import team.opentech.usher.util.ListUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月19日 14时54分
 */
public class TestDoubleFunctionPopulation extends AbstractPopulation<Double, Double> {

    public TestDoubleFunctionPopulation(Properties config) {
        super(config);
    }


    @Override
    protected TestQuadraticFunctionIndividual makeNewIndividual() {
        return new TestQuadraticFunctionIndividual();
    }

    @Override
    protected Double dealResults(List<Double> results) {
        return ListUtil.mean(results);
    }

}
