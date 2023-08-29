package team.opentech.usher.plan.pojo.plan.impl;

import java.util.Map;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.handler.MysqlTcpInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.plan.pojo.plan.UsePlan;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月29日 10时27分
 */
public class UseSqlPlanImpl extends UsePlan {

    public UseSqlPlanImpl(String database, Map<String, String> headers, Map<String, Object> params) {
        super(database, headers, params);
    }

    @Override
    public NodeInvokeResult invoke() {
        MysqlTcpInfo mysqlTcpInfo = MysqlContent.MYSQL_TCP_INFO.get();
        mysqlTcpInfo.setDatabase(database);
        return new NodeInvokeResult(this);
    }
}
