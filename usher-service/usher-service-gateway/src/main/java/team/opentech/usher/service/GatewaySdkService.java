package team.opentech.usher.service;

import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.service.MysqlSdkService;
import team.opentech.usher.pojo.cqe.InvokeCommand;

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


}
