package team.opentech.usher.core.fitness;

/**
 * 可以直接计算出结果的适应度函数
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月25日 14时35分
 */
public abstract class AbstractCalculateFitnessHandler<T, E> extends AbstractFitnessHandler<T, E> {

    @Override
    public Float quantifyGap(E calculationResult, T param) {
        E standardResult = calculateResult(param);
        return doQuantifyGap(standardResult, calculationResult);
    }


    /**
     * 量化差距
     *
     * @param standardResult    标准结果
     * @param calculationResult 模型计算的结果
     *
     * @return 差距量化后的值
     */
    protected abstract Float doQuantifyGap(E standardResult, E calculationResult);

}
