package team.opentech.usher.genetic.core.fitness;

/**
 * 可以直接计算出结果的适应度函数
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月25日 14时35分
 */
public abstract class AbstractCalculateFitnessHandler<T, E> extends AbstractFitnessHandler<T, E> {

    @Override
    public Double forwardAndLoss(E calculationResult, T param) {
        E standardResult = calculateResult(param);
        return loss(standardResult, calculationResult);
    }


    /**
     * 损失函数
     *
     * @param standardResult    标准结果
     * @param calculationResult 模型计算的结果
     *
     * @return 差距量化后的值
     */
    protected abstract Double loss(E standardResult, E calculationResult);

}
