package top.uhyils.usher.util.formula;

import java.util.Stack;
import org.apache.commons.lang3.math.NumberUtils;
import top.uhyils.usher.util.formula.enums.FunctionEnum;
import top.uhyils.usher.util.formula.impl.node.FunctionFormulaNodeImpl;
import top.uhyils.usher.util.formula.impl.node.LevelFormulaNodeImpl;
import top.uhyils.usher.util.formula.impl.node.NormalFormulaNodeImpl;
import top.uhyils.usher.util.formula.impl.node.SingleItemNodeImpl;

/**
 * 节点工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年12月28日 16时08分
 */
public class FormulaNodeFactory {


    /**
     * 举例用
     */
    private static final Object obj = new Object();

    /**
     * 根据公式创建一个公式节点
     *
     * @param formula
     *
     * @return
     */
    public static FormulaNode create(String formula) {
        for (FunctionEnum value : FunctionEnum.values()) {
            if (formula.startsWith(value.getName())) {
                int startIndex = formula.indexOf("(");
                int endIndex = formula.lastIndexOf(")");
                String substring = formula.substring(startIndex + 1, endIndex);
                if (bracketsReal(substring)) {
                    return new FunctionFormulaNodeImpl(formula, value.getName());
                }
            }
        }
        if (formula.contains("(")) {
            return new NormalFormulaNodeImpl(formula);
        }
        if (formula.contains("+")) {
            return new NormalFormulaNodeImpl(formula);
        }
        if (NumberUtils.isParsable(formula)) {
            return new LevelFormulaNodeImpl(formula);
        }
        return new SingleItemNodeImpl(formula);

    }

    /**
     * 判断括号是否两两配对
     *
     * @param substring
     *
     * @return
     */
    private static boolean bracketsReal(String substring) {
        Stack<Object> stack = new Stack<>();
        for (int i = 0; i < substring.length(); i++) {
            char c = substring.charAt(i);
            if (c == '(') {
                stack.push(obj);
                continue;
            }
            if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
}
