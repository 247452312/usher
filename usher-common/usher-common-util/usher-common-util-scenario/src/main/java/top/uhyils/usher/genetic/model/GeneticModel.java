package top.uhyils.usher.genetic.model;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import top.uhyils.usher.enums.DimensionalityReductionTypeEnum;
import top.uhyils.usher.enums.StandardizationTypeEnum;
import top.uhyils.usher.genetic.core.individual.Individual;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 08时44分
 */
public class GeneticModel implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 配置
     */
    private Properties populationConfig;

    /**
     * 降维方法类型
     */
    private DimensionalityReductionTypeEnum dimensionalityReductionType;

    /**
     * 降维矩阵
     */
    private double[][] dimensionalityReductionMatrix;

    /**
     * 标准化方法
     */
    private StandardizationTypeEnum standardizationType;

    /**
     * 结果集是否标准化
     */
    private Boolean resultStandardized;

    /**
     * 种群中所有的个体
     */
    private List<Individual<Double[], Double>> individuals;


    public Properties getPopulationConfig() {
        return populationConfig;
    }

    public void setPopulationConfig(Properties populationConfig) {
        this.populationConfig = populationConfig;
    }

    public DimensionalityReductionTypeEnum getDimensionalityReductionType() {
        return dimensionalityReductionType;
    }

    public void setDimensionalityReductionType(DimensionalityReductionTypeEnum dimensionalityReductionType) {
        this.dimensionalityReductionType = dimensionalityReductionType;
    }

    public double[][] getDimensionalityReductionMatrix() {
        return dimensionalityReductionMatrix;
    }

    public void setDimensionalityReductionMatrix(double[][] dimensionalityReductionMatrix) {
        this.dimensionalityReductionMatrix = dimensionalityReductionMatrix;
    }

    public StandardizationTypeEnum getStandardizationType() {
        return standardizationType;
    }

    public void setStandardizationType(StandardizationTypeEnum standardizationType) {
        this.standardizationType = standardizationType;
    }

    public Boolean getResultStandardized() {
        return resultStandardized;
    }

    public void setResultStandardized(Boolean resultStandardized) {
        this.resultStandardized = resultStandardized;
    }


    public List<Individual<Double[], Double>> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Individual<Double[], Double>> individuals) {
        this.individuals = individuals;
    }
}
