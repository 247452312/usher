package top.uhyils.usher.mysql.pojo.plan.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.enums.FieldTypeEnum;
import top.uhyils.usher.mysql.pojo.DTO.ExprParseResultInfo;
import top.uhyils.usher.mysql.pojo.DTO.FieldInfo;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.mysql.pojo.plan.BinarySqlPlan;
import top.uhyils.usher.mysql.util.MysqlUtil;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.MapUtil;
import top.uhyils.usher.util.StringUtil;

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
        /*left Expr 或者 right Expr 是多行的话,结果就是多行, 如果均不是指针,则指定结果*/
        fieldInfos.add(new FieldInfo("information_schema", "tables", "tables", MysqlContent.DEFAULT_RESULT_NAME, MysqlContent.DEFAULT_RESULT_NAME, 0, 1, FieldTypeEnum.FIELD_TYPE_VARCHAR, (short) 0, (byte) 0));
        nodeInvokeResult.setFieldInfos(fieldInfos);

        /*判断结果是什么*/
        List<Map<String, Object>> result = new ArrayList<>();
        ExprParseResultInfo<Object> left = MysqlUtil.parse(leftExpr, lastAllPlanResult, lastNodeInvokeResult);
        ExprParseResultInfo<Object> right = MysqlUtil.parse(rightExpr, lastAllPlanResult, lastNodeInvokeResult);

        // 如果两边都是常量,size为1 否则 哪边是列表size是哪边 如果两边都是,则使用左边字符串为准
        long size = !left.isConstant() ? left.getListResult().size() : (!right.isConstant() ? right.getListResult().size() : 1);

        for (int i = 0; i < size; i++) {
            Object leftItem = left.isConstant() ? left.get() : left.get(i);
            Object rightItem = right.isConstant() ? right.get() : right.get(i);
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
            Map<String, Object> e = MapUtil.singletonMap(MysqlContent.DEFAULT_RESULT_NAME, aFloat);
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
