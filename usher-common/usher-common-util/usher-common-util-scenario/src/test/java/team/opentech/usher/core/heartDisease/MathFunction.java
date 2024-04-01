package team.opentech.usher.core.heartDisease;


import com.google.common.collect.Maps.EntryTransformer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.CollectionUtil;
import team.opentech.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月01日 09时15分
 */
public enum MathFunction {
//    /**
//     * 乘方
//     */
//    POWER(0, powerClass(), false, (params, depVar) -> Math.pow(depVar, params[0].doubleValue()), (numbers, s) -> {
//
//        NumberFormat instance = NumberFormat.getInstance();
//        instance.setMaximumFractionDigits(4);
//        return s + "^" + instance.format(numbers[0]);
//    }),
//    /**
//     * sin
//     */
//    SIN(1, new ArrayList<>(), true, (params, depVar) -> Math.sin(depVar), (numbers, s) -> String.format("sin(%s)", s)),
//    /**
//     * log
//     */
//    LOG(2, new ArrayList<>(), false, (params, depVar) -> Math.log(depVar), (numbers, s) -> String.format("log(2,%s)", s)),
    /**
     * content
     */
    CONTENT(0, new ArrayList<>(), false, (params, depVar) -> depVar, (numbers, s) -> s),
    ;

    public static Map<Integer, MathFunction> cache = initCache();

    /**
     * 编码
     */
    private final Integer code;

    /**
     * 入参bit数量和类型
     */
    private final List<Pair<Integer, Class<? extends Number>>> classBitCountAndType;

    /**
     * 自变量是不是公式
     */
    private final Boolean depVarIsFormula;

    /**
     * 执行方法
     */
    private final EntryTransformer<Number[], Double, Double> function;

    /**
     * 公式表达
     */
    private final EntryTransformer<Number[], String, String> makeStrFunction;

    MathFunction(Integer code, List<Pair<Integer, Class<? extends Number>>> classBitCountAndType, Boolean depVarIsFormula, EntryTransformer<Number[], Double, Double> function, EntryTransformer<Number[], String, String> makeStrFunction) {
        this.code = code;
        this.classBitCountAndType = classBitCountAndType;
        this.depVarIsFormula = depVarIsFormula;
        this.function = function;
        this.makeStrFunction = makeStrFunction;
    }

    @NotNull
    public static MathFunction getByCode(int code) {
        code = code % cache.size();
        return cache.get(code);
    }

    public static int size() {
        return values().length;
    }

    private static List<Pair<Integer, Class<? extends Number>>> powerClass() {
        ArrayList<Pair<Integer, Class<? extends Number>>> pairs = new ArrayList<>();
        pairs.add(new Pair<>(6, Integer.class));
        return pairs;
    }

    private static Map<Integer, MathFunction> initCache() {
        MathFunction[] values = values();
        Map<Integer, MathFunction> result = new HashMap<>(values.length);
        for (MathFunction value : values) {
            result.put(value.getCode(), value);
        }
        return result;
    }

    public Integer getCode() {
        return code;
    }


    /**
     * 执行
     *
     * @param depVar 因变量
     * @param params 系数
     */
    public Double run(Double depVar, Number... params) {
        if (CollectionUtil.isNotEmpty(this.classBitCountAndType)) {
            Asserts.assertTrue(params.length == classBitCountAndType.size(), "数学方法" + this.name() + "中入参数量不等于定入参");
        }
        return function.transformEntry(params, depVar);
    }

    /**
     * string
     *
     * @param params 系数
     */
    public String makeString(char varName, Number... params) {
        if (CollectionUtil.isNotEmpty(this.classBitCountAndType)) {
            Asserts.assertTrue(params.length == classBitCountAndType.size(), "数学方法" + this.name() + "中入参数量不等于定入参");
        }
        return makeStrFunction.transformEntry(params, String.valueOf(varName));
    }

    public List<Pair<Integer, Class<? extends Number>>> getClassBitCountAndType() {
        return classBitCountAndType;
    }

    /**
     * 自变量能不能是一个公式
     */
    public Boolean depVarIsFormula() {
        return depVarIsFormula;
    }
}
