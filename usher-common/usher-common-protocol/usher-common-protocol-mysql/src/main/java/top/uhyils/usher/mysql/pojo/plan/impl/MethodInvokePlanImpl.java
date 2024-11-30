package top.uhyils.usher.mysql.pojo.plan.impl;

import com.alibaba.druid.sql.ast.SQLExpr;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.helpers.MessageFormatter;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.mysql.pojo.plan.MethodInvokePlan;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 16时33分
 */
public class MethodInvokePlanImpl extends MethodInvokePlan {


    public MethodInvokePlanImpl(Map<String, String> headers, Integer index, String methodName, List<SQLExpr> arguments, String asName) {
        super(headers, index, methodName, arguments, asName);
    }

    @Override
    public NodeInvokeResult invoke() {
        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        NodeInvokeResult nodeInvokeResult = new NodeInvokeResult(this);
        String fieldName = toFieldName();
        nodeInvokeResult.setFieldInfos(Collections.singletonList(methodEnum.makeFieldInfo(mysqlTcpLink.getDatabase(), MysqlContent.DEFAULT_METHOD_CALL_TABLE, MysqlContent.DEFAULT_METHOD_CALL_TABLE, this.index, fieldName)));
        nodeInvokeResult.setResult(methodEnum.makeResult(lastAllPlanResult, lastNodeInvokeResult, arguments, fieldName));
        return nodeInvokeResult;
    }

    private String toFieldName() {
        if (asName != null) {
            return asName;
        }
        String collect = arguments.stream().map(Object::toString).collect(Collectors.joining(","));
        return MessageFormatter.arrayFormat("{}({})", new Object[]{methodName, collect}).getMessage();
    }

}
