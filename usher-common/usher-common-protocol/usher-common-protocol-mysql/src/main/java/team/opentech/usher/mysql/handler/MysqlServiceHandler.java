package team.opentech.usher.mysql.handler;

import java.util.List;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mysql.pojo.DTO.DatabaseInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.pojo.DTO.TableDTO;
import team.opentech.usher.mysql.pojo.cqe.MysqlInvokeCommand;
import team.opentech.usher.mysql.pojo.cqe.TableQuery;
import team.opentech.usher.mysql.pojo.cqe.UserQuery;
import team.opentech.usher.mysql.pojo.cqe.impl.MysqlAuthCommand;
import team.opentech.usher.mysql.pojo.response.MysqlResponse;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.query.BlackQuery;

/**
 * mysql这一层需要的service
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月16日 17时19分
 */
public interface MysqlServiceHandler {

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


    /**
     * 执行远程请求
     *
     * @param command
     *
     * @return
     */
    NodeInvokeResult invokeInterface(MysqlInvokeCommand command);

    /**
     * 执行节点
     *
     * @param command
     *
     * @return
     */
    @NotNull
    NodeInvokeResult invokeSingleQuerySql(MysqlInvokeCommand command);


    /**
     * 查询user
     *
     * @param userQuery
     *
     * @return
     */
    List<UserDTO> queryUser(UserQuery userQuery);

    /**
     * 查询调用的表的信息
     *
     * @param tableQuery
     *
     * @return
     */
    List<TableDTO> queryTable(TableQuery tableQuery);

}
