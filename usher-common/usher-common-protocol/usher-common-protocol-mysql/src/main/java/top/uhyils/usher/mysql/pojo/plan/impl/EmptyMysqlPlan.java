package top.uhyils.usher.mysql.pojo.plan.impl;


import java.util.ArrayList;
import java.util.Map;
import top.uhyils.usher.mysql.plan.AbstractMysqlSqlPlan;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;

/**
 * 空白执行计划,什么都不做
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年04月01日 08时58分
 */
public class EmptyMysqlPlan extends AbstractMysqlSqlPlan {

    public EmptyMysqlPlan(Map<String, String> headers) {
        super(null, headers, null);
    }

    @Override
    public void complete(Map<Long, NodeInvokeResult> planArgs) {
    }

    @Override
    public NodeInvokeResult invoke() {
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(this);
        nodeInvokeResult.setFieldInfos(new ArrayList<>());
        nodeInvokeResult.setResult(new ArrayList<>());
        return nodeInvokeResult;
    }
}
