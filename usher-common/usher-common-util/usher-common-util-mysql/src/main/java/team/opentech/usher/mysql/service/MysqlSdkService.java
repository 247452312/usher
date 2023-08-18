package team.opentech.usher.mysql.service;

import team.opentech.usher.mysql.pojo.DTO.DatabaseInfo;
import team.opentech.usher.mysql.pojo.cqe.impl.MysqlAuthCommand;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;
import team.opentech.usher.pojo.cqe.query.BlackQuery;
import java.util.List;

/**
 * mysql这一层需要的service
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月16日 17时19分
 */
public interface MysqlSdkService {

    /**
     * mysql登录
     *
     * @param mysqlCommand
     *
     * @return
     */
    MysqlResponse mysqlLogin(MysqlAuthCommand mysqlCommand);

    /**
     * 获取有权限的数据库列表
     *
     * @param blackQuery
     *
     * @return
     */
    List<DatabaseInfo> getAllDatabaseInfo(BlackQuery blackQuery);
}
