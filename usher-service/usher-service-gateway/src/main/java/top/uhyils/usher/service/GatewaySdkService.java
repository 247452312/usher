package top.uhyils.usher.service;

import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.pojo.NodeInvokeResult;
import top.uhyils.usher.pojo.SqlInvokeCommand;

/**
 * sdk gateway对外提供的方法
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月15日 09时52分
 */
public interface GatewaySdkService extends BaseService {


    /**
     * 执行节点
     *
     * @param command
     *
     * @return
     */
    @NotNull
    NodeInvokeResult invokeCallNode(SqlInvokeCommand command);


}
