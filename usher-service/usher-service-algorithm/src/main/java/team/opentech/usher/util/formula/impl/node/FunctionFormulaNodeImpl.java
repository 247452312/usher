package team.opentech.usher.util.formula.impl.node;

import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.formula.FormulaNode;
import team.opentech.usher.util.formula.FormulaNodeFactory;
import team.opentech.usher.util.formula.enums.FunctionEnum;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 方法节点
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年12月24日 08时35分
 */
public class FunctionFormulaNodeImpl extends AbstractFormulaNode implements FormulaNode {

    /**
     * 方法名称
     */
    private String functionName;

    /**
     * 方法中入参里有好多代码
     */
    private List<FormulaNode> formulas;

    public FunctionFormulaNodeImpl(String formula, String functionName) {
        super(formula);
        this.functionName = functionName;
    }

    @Override
    public String getNodeFormula() {
        StringBuilder sb = new StringBuilder();
        for (FormulaNode normalFormulaNode : formulas) {
            sb.append(normalFormulaNode.getNodeFormula());
            sb.append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        return addFunctionName(sb.toString());
    }

    @Override
    public String getFormula() {
        return addFunctionName(super.getFormula());
    }

    @Override
    public List<String> getAllVarName() {
        return formulas.stream().map(FormulaNode::getAllVarName).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    protected void init() {
        //从公式中移除方法名称
        removeFunctionNameForFormula();

        this.formulas = Arrays.stream(formula.split(",")).map(FormulaNodeFactory::create).collect(Collectors.toList());
    }

    @Override
    protected String haveVarNameDerivation(String varName) {
        FunctionEnum parse = FunctionEnum.parse(functionName);
        Asserts.assertTrue(parse != null, "没有找到公式");
        return parse.derivation(getFormula(), varName);
    }

    @Override
    protected String dealFormula(String formula) {
        return formula;
    }

    /**
     * 从公式中移除方法名称
     */
    private void removeFunctionNameForFormula() {
        int start = formula.indexOf("(");
        int end = formula.lastIndexOf(")");
        formula = formula.substring(start + 1, end);
    }

    /**
     * 包裹方法体
     *
     * @param formula
     *
     * @return
     */
    private String addFunctionName(String formula) {
        return new StringBuilder().append(functionName).append("(").append(formula).append(")").toString();
    }
}
