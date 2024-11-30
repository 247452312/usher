package top.uhyils.usher.genetic.core.population;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;
import top.uhyils.usher.genetic.core.fitness.FitnessHandler;
import top.uhyils.usher.genetic.core.individual.AbstractIndividual;
import top.uhyils.usher.genetic.core.individual.Individual;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.Pair;

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
     * 混合式遗传算法,变异后引入有方向学习概念,此值是学习率
     */
    protected final Double learningRate;

    /**
     * 混合式遗传算法,变异后引入有方向学习概念,此值是梯度下降时计算学习率个体的比例
     */
    protected final Double learningPercentage;

    /**
     * 种群中的个体集合
     */
    private final List<Individual<T, E>> individuals = new ArrayList<>();


    public AbstractPopulation(Properties config) {
        this(Integer.parseInt(config.getProperty("scenario.population.K", "1000")),
             Double.parseDouble(config.getProperty("scenario.population.init-percentage", "0.2")),
             Double.parseDouble(config.getProperty("scenario.population.init-cross-percentage", "0.4")),
             Double.parseDouble(config.getProperty("scenario.population.init-variation-percentage", "0.1")),
             Double.parseDouble(config.getProperty("scenario.population.learning-percentage", VOTE_PERCENTAGE * 2 + "")),
             Double.parseDouble(config.getProperty("scenario.population.learning-rate", "0.01"))
        );
    }

    public AbstractPopulation(Properties config, List<Individual<T, E>> individuals) {
        this(config);
        this.individuals.addAll(individuals);
    }


    public AbstractPopulation(Integer k, Double initPercentage, Double crossPercentage, Double variationPercentage, Double learningPercentage, Double learningRate) {
        this.K = k;
        this.initPercentage = initPercentage;
        this.crossPercentage = crossPercentage;
        this.variationPercentage = variationPercentage;
        this.learningRate = learningRate;
        this.learningPercentage = learningPercentage;
    }

    @Override
    public void iteration(FitnessHandler<T, E> fitnessHandler) {
        iteration(fitnessHandler, 1);
    }

    @Override
    public void iteration(FitnessHandler<T, E> fitnessHandler, int size) {
        iteration(fitnessHandler, size, null);
    }

    @Override
    public void iteration(FitnessHandler<T, E> fitnessHandler, List<Pair<T, E>> learnParam) {
        iteration(fitnessHandler, 1, learnParam);
    }

    @Override
    public void iteration(FitnessHandler<T, E> fitnessHandler, int size, List<Pair<T, E>> learnParam) {
        for (int i = 0; i < size; i++) {
            choose(fitnessHandler);
            cross();
            variation(0);
            // 有方向变异
            directionalLearn(fitnessHandler, learnParam);
        }
    }

    @Override
    public E findResult(FitnessHandler<T, E> fitnessHandler, T params) {
        List<Individual<T, E>> individuals = fitnessHandler.findTopPercentage(allIndividuals(), VOTE_PERCENTAGE, initSize());
        List<E> results = individuals.stream().map(t -> t.findResult(params)).collect(Collectors.toList());
        return dealResults(results);
    }

    @Override
    public List<Individual<T, E>> allIndividuals() {
        return CollectionUtil.copyList(individuals);
    }

    @Override
    public Population<T, E> init() {
        individuals.clear();
        int initSize = initSize();
        for (int i = 0; i < initSize; i++) {
            Individual<T, E> individual = makeNewIndividual();
            individuals.add(individual);
        }
        return this;
    }

    /**
     * 随机变异
     */
    protected void variation(Integer virusDna) {
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
    protected void choose(FitnessHandler<T, E> fitnessHandler) {
        List<Individual<T, E>> topPercentage = fitnessHandler.findTopPercentage(individuals, crossPercentage / (1 + crossPercentage), K);
        individuals.clear();
        individuals.addAll(topPercentage);
    }

    /**
     * 创建新个体
     */
    protected abstract Individual<T, E> makeNewIndividual();

    /**
     * 如何将多个结果合成一个结果
     */
    protected abstract E dealResults(List<E> results);


    /**
     * 混合遗传算法中通过神经网络的梯度下降方法进行有方向学习,当前方法的实现方式应该是通过对比入参中的结果和目标结果,求得差值或者平均差值,然后根据学习率分别对编码的各个参数进行微调
     *
     * @param learnParam
     */
    private void directionalLearn(FitnessHandler<T, E> fitnessHandler, List<Pair<T, E>> learnParam) {
        // 没有提供学习部分,则不需要进行学习
        if (CollectionUtil.isEmpty(learnParam)) {
            return;
        }
        List<Individual<T, E>> topPercentage = fitnessHandler.findTopPercentage(individuals, learningPercentage, 1);

        // 遍历所有个体,计算结果,和指定结果进行比较,求出梯度下降程度
        for (Individual<T, E> individual : topPercentage) {
            if (individual instanceof AbstractIndividual) {
                ((AbstractIndividual<T, E>) individual).clearCache();
            }
            for (Pair<T, E> item : learnParam) {
                individual.directionalLearn(item.getKey(), item.getValue(), this.learningRate);
            }
        }

    }

    /**
     * 初始化种群大小
     */
    private int initSize() {
        return (int) (K * initPercentage);
    }
}
