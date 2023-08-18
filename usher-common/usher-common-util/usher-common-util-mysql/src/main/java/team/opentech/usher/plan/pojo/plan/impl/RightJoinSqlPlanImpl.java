package team.opentech.usher.plan.pojo.plan.impl;

import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.plan.pojo.SqlTableSourceBinaryTree;
import team.opentech.usher.plan.pojo.plan.RightJoinSqlPlan;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 16时34分
 */
public class RightJoinSqlPlanImpl extends RightJoinSqlPlan {

    public RightJoinSqlPlanImpl(Map<String, String> headers, SqlTableSourceBinaryTree tree, Long leftPlanId, Long rightPlanId) {
        super(headers, tree, leftPlanId, rightPlanId);
    }

    @Override
    public NodeInvokeResult invoke() {
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(this);
        nodeInvokeResult.setFieldInfos(allFieldInfo());
        nodeInvokeResult.setResult(new ArrayList<>());
        return nodeInvokeResult;
    }
}
