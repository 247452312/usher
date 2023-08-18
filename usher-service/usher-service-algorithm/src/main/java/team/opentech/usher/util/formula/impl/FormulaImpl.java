package team.opentech.usher.util.formula.impl;

import team.opentech.usher.util.formula.Formula;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年12月22日 14时20分
 */
public class FormulaImpl implements Formula {

    /**
     * 公式本体
     */
    private String formula;

    /**
     * 因变量
     */
    private List<String> independentVariables;

    @Override
    public String getFormula() {
        return formula;
    }

    @Override
    public Formula derivation() {
        return null;
    }
}
