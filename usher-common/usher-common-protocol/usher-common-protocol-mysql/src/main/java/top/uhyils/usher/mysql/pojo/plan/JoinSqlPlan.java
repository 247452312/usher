package top.uhyils.usher.mysql.pojo.plan;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumberExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumericLiteralExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import top.uhyils.usher.annotation.Nullable;
import top.uhyils.usher.mysql.plan.AbstractMysqlSqlPlan;
import top.uhyils.usher.mysql.pojo.DTO.FieldInfo;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.StringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年07月31日 15时35分
 */
public abstract class JoinSqlPlan extends AbstractMysqlSqlPlan {

    /**
     * 左边执行计划id
     */
    protected Long leftResultPlanId;

    /**
     * 左边结果
     */
    protected NodeInvokeResult leftResult;


    /**
     * 左树
     */
    protected SqlTableSourceBinaryTreeInfo leftTree;

    /**
     * 右边执行计划id
     */
    protected Long rightResultPlanId;

    /**
     * 右边结果
     */
    protected NodeInvokeResult rightResult;

    /**
     * 右树
     */
    protected SqlTableSourceBinaryTreeInfo rightTree;

    /**
     * 连接条件
     */
    protected SQLBinaryOpExpr condition;

    protected JoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId) {
        super("select * from " + tree.getLeftTree().getTableSource().getName() + " " + tree.getCondition().toString() + " " + tree.getRightTree().getTableSource().getName(), headers, new HashMap<>());
        this.leftResultPlanId = leftPlanId;
        this.rightResultPlanId = rightPlanId;
        this.leftTree = tree.getLeftTree();
        this.rightTree = tree.getRightTree();
        this.condition = tree.getCondition();
    }

    /**
     * 根据子树查询结果
     *
     * @param tree
     *
     * @return
     */
    private static List<FieldInfo> findResultByTree(SqlTableSourceBinaryTreeInfo tree, NodeInvokeResult results) {
        SQLExprTableSource tableSource = tree.getTableSource();
        String rightAlias = tableSource.getAlias();
        if (StringUtil.isEmpty(rightAlias)) {
            rightAlias = ((SQLPropertyExpr) tableSource.getExpr()).getName();
        }
        String finalRightAlias = rightAlias;
        return results.getFieldInfos().stream().map(s -> s.copyWithNewFieldName(finalRightAlias, s.getFieldName())).collect(Collectors.toList());
    }

    @Nullable
    private static Object parseExpr(Map<String, Object> leftResult, Map<String, Object> rightResult, String leftAlias, String rightAlias, SQLExpr left) {
        Object leftLineItem = null;
        if (left instanceof SQLPropertyExpr) {
            String name = ((SQLPropertyExpr) left).getName();
            String leftOwnerName = ((SQLPropertyExpr) left).getOwnernName();
            if (StringUtil.isEmpty(leftOwnerName)) {
                if (leftResult.containsKey(name)) {
                    leftLineItem = leftResult.get(name);
                } else if (rightResult.containsKey(name)) {
                    leftLineItem = rightResult.get(name);
                } else {
                    Asserts.throwException("join操作时未找到对应的属性:{}", name);
                }
            } else if (Objects.equals(leftOwnerName, leftAlias)) {
                if (leftResult.containsKey(name)) {
                    leftLineItem = leftResult.get(name);
                } else {
                    Asserts.throwException("join操作时未找到`{}`对应的属性:{}", leftAlias, name);
                }
            } else if (Objects.equals(leftOwnerName, rightAlias)) {
                if (rightResult.containsKey(name)) {
                    leftLineItem = rightResult.get(name);
                } else {
                    Asserts.throwException("join操作时未找到`{}`对应的属性:{}", leftAlias, name);
                }
            }
        } else if (left instanceof SQLCharExpr) {
            leftLineItem = top.uhyils.usher.mysql.util.StringUtil.cleanQuotation(((SQLCharExpr) left).getText());
        } else if (left instanceof SQLIdentifierExpr) {
            leftLineItem = ((SQLIdentifierExpr) left).getName();
        } else if (left instanceof SQLNumberExpr) {
            leftLineItem = ((SQLNumberExpr) left).getValue();
        } else if (left instanceof SQLNumericLiteralExpr) {
            leftLineItem = ((SQLNumericLiteralExpr) left).getNumber();
        } else {
            Asserts.throwException("join操作时解析on失败:{},类型为:{}", left.toString(), left.getClass().getName());
        }
        return leftLineItem;
    }

    @Override
    public void complete(Map<Long, NodeInvokeResult> planArgs) {
        this.leftResult = planArgs.get(leftResultPlanId);
        this.rightResult = planArgs.get(rightResultPlanId);
    }

    /**
     * 整合所有的fieldInfo
     *
     * @return
     */
    protected List<FieldInfo> allFieldInfo() {
        Set<FieldInfo> result = new HashSet<>();
        result.addAll(findResultByTree(leftTree, leftResult));
        result.addAll(findResultByTree(rightTree, rightResult));
        return new ArrayList<>(result);
    }

    /**
     * 切割on连接字符
     *
     * @return
     */
    protected List<List<SQLBinaryOpExpr>> splitCondition() {
        if (condition == null) {
            return new ArrayList<>();
        }
        List<List<SQLBinaryOpExpr>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        splitCondition(condition, result);
        return result;
    }

    /**
     * 检查两行是否可以合并,外层判断,此时存在or 每一个or判断中只要有一个true即可返回true
     *
     * @param leftResult
     * @param rightResult
     * @param on
     * @param leftAlias
     * @param rightAlias
     *
     * @return
     */
    protected boolean checkMerge(Map<String, Object> leftResult, Map<String, Object> rightResult, List<List<SQLBinaryOpExpr>> on, String leftAlias, String rightAlias) {
        // 外层是or 只要有一个true即可返回
        for (List<SQLBinaryOpExpr> sqlBinaryOpExprs : on) {
            boolean b = checkInnerMerge(leftResult, rightResult, sqlBinaryOpExprs, leftAlias, rightAlias);
            if (Boolean.TRUE.equals(b)) {
                return true;
            }
        }
        return false;

    }

    /**
     * 检查两行是否可以合并,内层判断,此时没有or,只有and多个条件判断, 只要有一个false即可返回false
     *
     * @param leftResult
     * @param rightResult
     * @param sqlBinaryOpExprs
     * @param leftAlias
     * @param rightAlias
     *
     * @return
     */
    private boolean checkInnerMerge(Map<String, Object> leftResult, Map<String, Object> rightResult, List<SQLBinaryOpExpr> sqlBinaryOpExprs, String leftAlias, String rightAlias) {
        for (SQLBinaryOpExpr sqlBinaryOpExpr : sqlBinaryOpExprs) {
            SQLBinaryOperator operator = sqlBinaryOpExpr.getOperator();
            SQLExpr left = sqlBinaryOpExpr.getLeft();
            SQLExpr right = sqlBinaryOpExpr.getRight();
            Object leftLineItem = parseExpr(leftResult, rightResult, leftAlias, rightAlias, left);
            Object rightLineItem = parseExpr(leftResult, rightResult, leftAlias, rightAlias, right);
            boolean canMerge = canMerge(leftLineItem, rightLineItem, operator);
            if (!canMerge) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断当前这两个值是否通过符号对应
     *
     * @param leftLineItem
     * @param rightLineItem
     * @param operator
     *
     * @return
     */
    private boolean canMerge(Object leftLineItem, Object rightLineItem, SQLBinaryOperator operator) {
        if (leftLineItem == null || rightLineItem == null) {
            return false;
        }
        // 等号单独判断
        if (operator == SQLBinaryOperator.Equality) {
            return Objects.equals(leftLineItem, rightLineItem);
        }
        Asserts.assertTrue((leftLineItem instanceof Number || StringUtil.isDigit((String) leftLineItem)) && (rightLineItem instanceof Number || StringUtil.isDigit((String) rightLineItem)), "on中条件错误: 条件不能进行符号比较,符号:{},left:{},right:{}", operator.name, leftLineItem, rightLineItem);

        if (leftLineItem instanceof Number) {
            leftLineItem = new BigDecimal(String.valueOf(leftLineItem));
        } else {
            leftLineItem = new BigDecimal((String) leftLineItem);
        }
        if (rightLineItem instanceof Number) {
            rightLineItem = new BigDecimal(String.valueOf(rightLineItem));
        } else {
            rightLineItem = new BigDecimal((String) rightLineItem);
        }

        switch (operator) {
            case LessThan:
                return ((BigDecimal) leftLineItem).doubleValue() < ((BigDecimal) rightLineItem).doubleValue();
            case LessThanOrEqual:
                return ((BigDecimal) leftLineItem).doubleValue() <= ((BigDecimal) rightLineItem).doubleValue();
            case GreaterThan:
                return ((BigDecimal) leftLineItem).doubleValue() > ((BigDecimal) rightLineItem).doubleValue();
            case GreaterThanOrEqual:
                return ((BigDecimal) leftLineItem).doubleValue() >= ((BigDecimal) rightLineItem).doubleValue();
            default:
                Asserts.throwException("暂不支持符号:{}", operator.name);
                return false;
        }
    }

    /**
     * 切割
     *
     * @param condition
     *
     * @return
     */
    private void splitCondition(SQLBinaryOpExpr condition, List<List<SQLBinaryOpExpr>> result) {
        SQLBinaryOperator operator = condition.getOperator();
        if (operator != SQLBinaryOperator.BooleanAnd && operator != SQLBinaryOperator.BooleanOr) {
            for (List<SQLBinaryOpExpr> binaryOpExprs : result) {
                binaryOpExprs.add(condition);
            }
            return;
        }

        if (operator == SQLBinaryOperator.BooleanOr) {
            List<List<SQLBinaryOpExpr>> lists = new ArrayList<>(result);
            splitCondition((SQLBinaryOpExpr) condition.getLeft(), result);
            splitCondition((SQLBinaryOpExpr) condition.getRight(), lists);
            return;
        }
        splitCondition((SQLBinaryOpExpr) condition.getLeft(), result);
        splitCondition((SQLBinaryOpExpr) condition.getRight(), result);
    }


}
