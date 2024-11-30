package top.uhyils.usher.enums;

import java.util.Objects;
import top.uhyils.usher.extracted.ExtractedFunction;
import top.uhyils.usher.extracted.ZScoreFunction;


/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月17日 08时37分
 */
public enum StandardizationTypeEnum {
    /**
     * 意义同name
     */
    Z_SCORE(1, "Z-score标准化法", new ZScoreFunction()),
    ;

    private final Integer code;

    private final String name;

    private final ExtractedFunction function;

    StandardizationTypeEnum(Integer code, String name, ExtractedFunction function) {
        this.code = code;
        this.name = name;
        this.function = function;
    }

    public static StandardizationTypeEnum getByCode(Integer code) {
        for (StandardizationTypeEnum value : values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 数据标准化
     *
     * @param data               数据
     * @param needDimensionIndex 需要标准化的列的下标
     *
     * @return
     */
    public double[][] extracted(double[][] data, int[] needDimensionIndex) {
        return function.extracted(data, needDimensionIndex);
    }

    /**
     * 数据标准化
     *
     * @param data 数据
     *
     * @return
     */
    public double[] extracted(double[] data) {
        return function.extracted(data);
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
