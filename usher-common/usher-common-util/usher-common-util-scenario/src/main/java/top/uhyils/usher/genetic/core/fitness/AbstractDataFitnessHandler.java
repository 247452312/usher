package top.uhyils.usher.genetic.core.fitness;

import java.util.Map;
import top.uhyils.usher.genetic.core.data.AbstractHistoryData;

/**
 * 通过历史数据去计算结果的适用度函数
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月25日 14时35分
 */
public abstract class AbstractDataFitnessHandler<T, E> extends AbstractFitnessHandler<T, E> {


    /**
     * 历史数据的入参和结果
     */
    protected final Map<T, E> testData;

    /**
     * 根据历史入参和结果拟合的规则曲线?
     */
    protected final AbstractHistoryData<T, E> fittingIndividual;

    protected AbstractDataFitnessHandler(Map<T, E> testData) {
        this.testData = testData;
        this.fittingIndividual = makeFittingIndividual(testData);
    }

    @Override
    public E calculateResult(T param) {
        return fittingIndividual.findResult(param);
    }

    /**
     * 根据历史入参和结果拟合规则
     *
     * @param testData 历史入参和结果
     *
     * @return 拟合后规则
     */
    protected abstract AbstractHistoryData<T, E> makeFittingIndividual(Map<T, E> testData);

    @Override
    protected Double forwardAndLoss(E calculationResult, T param) {
        //  我知道的东西:
        // 1.模型计算入参
        // 2.模型计算结果
        // 3.模型入参在历史数据中的计算结果
        E historicalResult;
        if (testData.containsKey(param)) {
            historicalResult = testData.get(param);
        } else {
            historicalResult = fittingIndividual.findResult(param);
        }

        // 那么我们可以根据模型计算入参根据历史数据找到结果之后 计算结果和模型计算入参的结果
        Double gap = loss(historicalResult, calculationResult);
        // todo 如果结果是从testData中来,并且结果不是完全匹配,那么说明并不正常,要不就是缺少了计算维度,需要通知并找到缺失的维度,要不就是计算概率问题
        return gap;
    }


    /**
     * 损失函数 损失函数越接近0表示越正确
     *
     * @param historicalResult  标准结果
     * @param calculationResult 模型计算的结果
     *
     * @return 差距量化后的值
     */
    protected abstract Double loss(E historicalResult, E calculationResult);
}
