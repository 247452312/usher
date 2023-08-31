package team.opentech.usher.mysql.pojo.plan.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mysql.enums.FieldTypeEnum;
import team.opentech.usher.mysql.pojo.DTO.FieldInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.pojo.plan.BinarySqlPlan;
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.StringUtil;

/**
 * 表达式执行计划
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月20日 13时13分
 */
public class BinarySqlPlanImpl extends BinarySqlPlan {

    public BinarySqlPlanImpl(Map<String, String> headers, SQLExpr leftExpr, SQLBinaryOperator operator, SQLExpr rightExpr) {
        super(null, headers, leftExpr, operator, rightExpr);
    }

    @Override
    public NodeInvokeResult invoke() {
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(this);
        List<FieldInfo> fieldInfos = new ArrayList<>();
        FieldTypeEnum fieldTypeVarchar = FieldTypeEnum.FIELD_TYPE_VARCHAR;

        /*left Expr 或者 right Expr 是多行的话,结果就是多行, 如果均不是指针,则指定结果*/
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", "result", "result", 0, 1, fieldTypeVarchar, (short) 0, (byte) 0));
        nodeInvokeResult.setFieldInfos(fieldInfos);
        List<Map<String, Object>> result = new ArrayList<>();
        List<Object> left = MysqlUtil.parse(leftExpr, lastAllPlanResult, lastNodeInvokeResult);
        List<Object> right = MysqlUtil.parse(rightExpr, lastAllPlanResult, lastNodeInvokeResult);
        for (int i = 0; i < left.size(); i++) {
            Object leftItem = left.get(i);
            Object rightItem = right.get(i);
            Float aFloat;
            /*1.如果一方为null,则结果为null*/
            if (leftItem == null || rightItem == null) {
                aFloat = null;
            } else if (leftItem instanceof Number && rightItem instanceof Number) {
                /*2.如果两个都是数值型,则直接做运算*/
                aFloat = calculate(operator, ((Number) leftItem).floatValue(), ((Number) rightItem).floatValue());
            } else if (leftItem instanceof Number) {
                /*3.如果其中一个是字符,则尝试转换为数字后再进行运算*/
                /*4.如果转换失败,则使用0替代*/
                aFloat = calculate(operator, (Number) leftItem, (String) rightItem);
            } else {
                aFloat = calculate(operator, (Number) rightItem, (String) leftItem);
            }
            Map<String, Object> e = new HashMap<>();
            e.put("result", aFloat);
            result.add(e);
        }
        nodeInvokeResult.setResult(result);
        return nodeInvokeResult;
    }

    /**
     * 计算
     *
     * @param operator
     * @param number
     * @param str
     *
     * @return
     */
    private Float calculate(SQLBinaryOperator operator, Number number, String str) {
        Float rightItemFloat = StringUtil.isDigit(str) ? Float.valueOf(str) : 0F;
        return calculate(operator, number.floatValue(), rightItemFloat);
    }

    /**
     * 计算
     *
     * @param operator
     * @param leftItem
     * @param rightItem
     *
     * @return
     */
    private Float calculate(SQLBinaryOperator operator, @NotNull Float leftItem, @NotNull Float rightItem) {

        switch (operator) {
            case Multiply:
                return leftItem * rightItem;
            case Divide:
                return leftItem / rightItem;
            case Modulus:
                return leftItem % rightItem;
            case Add:
                return leftItem + rightItem;
            case Subtract:
                return leftItem - rightItem;
            default:
                Asserts.throwException("未知的表达式符号");
        }
        return null;
    }
}
