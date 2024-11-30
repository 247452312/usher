package top.uhyils.usher.enums;

import java.util.Objects;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import top.uhyils.usher.dimensionality.DimensionalityReductionFunction;
import top.uhyils.usher.dimensionality.PCAFunction;


/**
 * 降维类型
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 08时27分
 */
public enum DimensionalityReductionTypeEnum {
    /**
     * 意义同name
     */
    PCA(1, "主成分分析", new PCAFunction()),
    // 缺失值比率（Missing Value Ratio）
    // 低方差滤波（Low Variance Filter）
    // 高相关滤波（High Correlation filter）
    // 随机森林（Random Forest）
    // 反向特征消除（Backward Feature Elimination）
    // 前向特征选择（Forward Feature Selection）
    // 因子分析（Factor Analysis）
    // 独立分量分析（ICA）
    // IOSMAP
    // t-SNE
    // UMAP
    ;

    private final Integer code;

    private final String name;

    private final DimensionalityReductionFunction dimensionalityReductionFunction;

    DimensionalityReductionTypeEnum(Integer code, String name, DimensionalityReductionFunction dimensionalityReductionFunction) {
        this.code = code;
        this.name = name;
        this.dimensionalityReductionFunction = dimensionalityReductionFunction;
    }

    public static DimensionalityReductionTypeEnum getByCode(Integer code) {
        for (DimensionalityReductionTypeEnum value : values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 降维
     *
     * @param data     待降维数据
     * @param drMatrix 降维矩阵
     *
     * @return
     */
    public static double[][] dimensionalityReduction(double[][] data, double[][] drMatrix) {
        RealMatrix realMatrix = MatrixUtils.createRealMatrix(drMatrix);
        // 目标矩阵 = 原始数据矩阵 * 转换矩阵  完成降维与标准化操作
        RealMatrix multiply = MatrixUtils.createRealMatrix(data).multiply(realMatrix);
        return multiply.getData();
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double[][] createDimensionalityReductionMatrix(double[][] key) {
        return dimensionalityReductionFunction.createDimensionalityReductionMatrix(key);
    }
}
