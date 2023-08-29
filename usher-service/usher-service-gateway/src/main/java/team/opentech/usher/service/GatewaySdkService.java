package team.opentech.usher.service;

import java.util.List;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.service.MysqlSdkService;
import team.opentech.usher.pojo.DTO.CallNodeDTO;
import team.opentech.usher.pojo.DTO.CompanyDTO;
import team.opentech.usher.pojo.cqe.CallNodeQuery;
import team.opentech.usher.pojo.cqe.InvokeCommand;
import team.opentech.usher.pojo.cqe.UserQuery;

/**
 * sdk gateway对外提供的方法
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月15日 09时52分
 */
public interface GatewaySdkService extends BaseService, MysqlSdkService {

    /**
     * 执行远程请求
     *
     * @param command
     *
     * @return
     */
    NodeInvokeResult invokeInterface(InvokeCommand command);

    /**
     * 执行节点
     *
     * @param command
     *
     * @return
     */
    @NotNull
    NodeInvokeResult invokeCallNode(InvokeCommand command);


    /**
     * 查询user
     *
     * @param userQuery
     *
     * @return
     */
    List<CompanyDTO> queryUser(UserQuery userQuery);

    /**
     * 查询调用节点
     *
     * @param callNodeQuery
     *
     * @return
     */
    List<CallNodeDTO> queryCallNode(CallNodeQuery callNodeQuery);
}
