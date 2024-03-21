package team.opentech.usher.core;

import java.util.List;
import java.util.Properties;
import team.opentech.usher.FitnessHandler;
import team.opentech.usher.util.ListUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月19日 14时54分
 */
public class TestPopulation extends AbstractPopulation<Float, Float> {

    public TestPopulation(Properties config, FitnessHandler<Float, Float> fitnessHandler) {
        super(config, fitnessHandler);
    }

    @Override
    protected TestIndividual makeNewIndividual() {
        return new TestIndividual();
    }

    @Override
    protected Float dealResults(List<Float> results) {
        return ListUtil.median(results);
    }
}
