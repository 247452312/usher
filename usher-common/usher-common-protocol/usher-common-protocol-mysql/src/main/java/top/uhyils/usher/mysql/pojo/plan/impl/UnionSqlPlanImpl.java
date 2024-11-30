package top.uhyils.usher.mysql.pojo.plan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.mysql.pojo.plan.UnionSqlPlan;
import top.uhyils.usher.util.CollectionUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 16时34分
 */
public class UnionSqlPlanImpl extends UnionSqlPlan {

    public UnionSqlPlanImpl(Map<String, String> headers, List<Long> unionPlanId) {
        super(null, headers, unionPlanId);
    }

    @Override
    public NodeInvokeResult invoke() {
        List<NodeInvokeResult> lastUnionResults = unionPlanIds.stream().map(t -> lastAllPlanResult.get(t)).filter(Objects::nonNull).collect(Collectors.toList());
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(this);
        nodeInvokeResult.setFieldInfos(new ArrayList<>());
        nodeInvokeResult.setResult(new ArrayList<>());
        if (CollectionUtil.isEmpty(lastUnionResults)) {
            return nodeInvokeResult;
        }
        NodeInvokeResult example = lastUnionResults.get(0);
        nodeInvokeResult.setFieldInfos(example.getFieldInfos());
        lastUnionResults.forEach(t -> nodeInvokeResult.getResult().addAll(t.getResult()));
        return nodeInvokeResult;
    }
}
