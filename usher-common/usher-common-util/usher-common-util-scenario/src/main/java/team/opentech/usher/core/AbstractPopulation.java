package team.opentech.usher.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;
import team.opentech.usher.FitnessHandler;
import team.opentech.usher.Individual;
import team.opentech.usher.Population;
import team.opentech.usher.util.CollectionUtil;

/**
 * 种群模板
 *
 * @param <T> 种群的入参
 * @param <E> 种群的出参
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月15日 16时06分
 */
public abstract class AbstractPopulation<T, E> implements Population<T, E> {

    /**
     * 种群中有表决权的百分比
     */
    private static final Double VOTE_PERCENTAGE = 10d / 100;

    /**
     * 适应度函数
     */
    protected final FitnessHandler<T, E> fitnessHandler;

    /**
     * 种群K值
     */
    protected final Integer K;

    /**
     * 初始化百分比
     */
    protected final Double initPercentage;

    /**
     * 出生率
     */
    protected final Double crossPercentage;

    /**
     * 变异率
     */
    protected final Double variationPercentage;

    /**
     * 种群中的个体集合
     */
    private final List<Individual<T, E>> individuals = new ArrayList<>();


    public AbstractPopulation(Properties config, FitnessHandler<T, E> fitnessHandler) {
        this(Integer.parseInt(config.getProperty("scenario.population.K", "1000")),
             Double.parseDouble(config.getProperty("scenario.population.init-percentage", "0.2")),
             Double.parseDouble(config.getProperty("scenario.population.init-cross-percentage", "0.4")),
             Double.parseDouble(config.getProperty("scenario.population.init-variation-percentage", "0.1")),
             fitnessHandler);
    }

    public AbstractPopulation(Integer k, Double initPercentage, Double crossPercentage, Double variationPercentage, FitnessHandler<T, E> fitnessHandler) {
        this.fitnessHandler = fitnessHandler;
        this.K = k;
        this.initPercentage = initPercentage;
        this.crossPercentage = crossPercentage;
        this.variationPercentage = variationPercentage;
    }

    @Override
    public void iteration() {
        iteration(1);
    }

    @Override
    public void iteration(int size) {
        for (int i = 0; i < size; i++) {
            choose();
            cross();
            variation(new byte[0]);
        }
    }

    @Override
    public E findResult(T params) {
        List<Individual<T, E>> individuals = fitnessHandler.findTopPercentage(allIndividuals(), VOTE_PERCENTAGE, initSize());
        List<E> results = individuals.stream().map(t -> t.findResult(params)).collect(Collectors.toList());
        return dealResults(results);
    }

    @Override
    public List<Individual<T, E>> allIndividuals() {
        return CollectionUtil.copyList(individuals);
    }

    @Override
    public void init() {
        individuals.clear();
        int initSize = initSize();
        for (int i = 0; i < initSize; i++) {
            Individual<T, E> individual = makeNewIndividual();
            individuals.add(individual);
        }
    }


    /**
     * 变异
     */
    protected void variation(byte[] virusDna) {
        int variationCount = (int) (individuals.size() * variationPercentage);
        int voteCount = (int) (individuals.size() * VOTE_PERCENTAGE);
        Random random = new Random();
        for (int i = 0; i < variationCount; i++) {
            int index = random.nextInt(individuals.size());
            if (index <= voteCount) {
                // 有表决权的个体对变异具有豁免权,将变异动作转移到没有表决权的个体上,这样保证了变异率不变,又能保证较优个体不变异
                int secondIndex = random.nextInt(individuals.size() - voteCount);
                index = secondIndex + voteCount;
            }
            Individual<T, E> individual = individuals.get(index);
            individual.variation(virusDna);
        }
    }

    /**
     * 交叉
     */
    protected void cross() {
        int willAddCount = (int) (individuals.size() * crossPercentage);
        List<Individual<T, E>> ml = new ArrayList<>();
        List<Individual<T, E>> fl = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < willAddCount; i++) {
            int index = random.nextInt(individuals.size());
            Individual<T, E> individual = individuals.get(index);
            fl.add(individual);

            index = random.nextInt(individuals.size());
            individual = individuals.get(index);
            ml.add(individual);
        }
        for (int i = 0; i < willAddCount; i++) {
            Individual<T, E> f = fl.get(i);
            Individual<T, E> m = ml.get(i);
            Individual<T, E> cross = f.cross(m);
            individuals.add(cross);
        }
    }

    /**
     * 选择
     */
    protected void choose() {
        List<Individual<T, E>> topPercentage = fitnessHandler.findTopPercentage(individuals, crossPercentage / (1 + crossPercentage), K);
        individuals.clear();
        individuals.addAll(topPercentage);
    }

    /**
     * 创建新个体
     */
    protected abstract Individual<T, E> makeNewIndividual();

    /**
     * 如何处理前百分比的数量
     */
    protected abstract E dealResults(List<E> results);

    /**
     * 初始化种群大小
     */
    private int initSize() {
        return (int) (K * initPercentage);
    }
}
