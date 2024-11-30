package top.uhyils.usher.mysql.pojo.plan.impl;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;
import top.uhyils.usher.mysql.pojo.plan.InnerJoinSqlPlan;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.MapUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 16时32分
 */
public class InnerJoinSqlPlanImpl extends InnerJoinSqlPlan {

    public InnerJoinSqlPlanImpl(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId) {
        super(headers, tree, leftPlanId, rightPlanId);
    }

    @Override
    public NodeInvokeResult invoke() {
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(this);
        nodeInvokeResult.setFieldInfos(allFieldInfo());
        /*此处两个不同行列数的table 需要融合在一起 on中的条件是融合前需要遵守的,也是合并表的依据 where 是合并后进行筛选*/
        // on里的条件
        List<List<SQLBinaryOpExpr>> lists = splitCondition();

        List<Map<String, Object>> leftResults = this.leftResult.getResult();
        List<Map<String, Object>> rightResults = this.rightResult.getResult();

        List<Map<String, Object>> result = new ArrayList<>();
        String leftAlias = leftTree.getTableSource().getAlias();
        String rightAlias = rightTree.getTableSource().getAlias();

        List<Map<String, Object>> leftCopy = CollectionUtil.copyList(leftResults);
        List<Map<String, Object>> rightCopy = CollectionUtil.copyList(rightResults);

        for (Map<String, Object> rightResult : rightResults) {
            for (Map<String, Object> leftResult : leftResults) {
                // 如果这是一个可以合并的行
                if (checkMerge(leftResult, rightResult, lists, leftAlias, rightAlias)) {
                    Map<String, Object> copy = MapUtil.copy(rightResult);
                    copy.putAll(leftResult);
                    result.add(copy);
                    leftCopy.remove(leftResult);
                    rightCopy.remove(rightResult);
                }
            }
        }
        //这里加left
        for (Map<String, Object> leftResult : leftCopy) {
            Map<String, Object> copy = MapUtil.copy(leftResult);
            result.add(copy);
        }
        //这里加 right
        for (Map<String, Object> rightResult : rightCopy) {
            Map<String, Object> copy = MapUtil.copy(rightResult);
            result.add(copy);
        }
        nodeInvokeResult.setResult(result);
        return nodeInvokeResult;
    }
}
