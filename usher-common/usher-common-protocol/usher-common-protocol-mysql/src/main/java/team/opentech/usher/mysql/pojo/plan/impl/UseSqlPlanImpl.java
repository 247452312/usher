package team.opentech.usher.mysql.pojo.plan.impl;

import java.util.Map;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.pojo.entity.MysqlTcpLink;
import team.opentech.usher.mysql.pojo.plan.UsePlan;

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
        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        mysqlTcpLink.setDatabase(database);
        return new NodeInvokeResult(this);
    }
}
