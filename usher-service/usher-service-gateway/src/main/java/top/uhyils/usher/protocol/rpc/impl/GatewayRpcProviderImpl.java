package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import top.uhyils.usher.enums.QuerySqlTypeEnum;
import top.uhyils.usher.pojo.NodeInvokeResult;
import top.uhyils.usher.pojo.SqlInvokeCommand;
import top.uhyils.usher.pojo.cqe.SdkSqlInvokeCommand;
import top.uhyils.usher.protocol.rpc.GatewayRpcProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.GatewaySdkService;

/**
 * 对外 rpc协议
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月12日 09时06分
 */
@RpcService
public class GatewayRpcProviderImpl implements GatewayRpcProvider {

    @Resource
    private GatewaySdkService service;

    @Override
    public List<Map<String, Object>> invokeRpc(SdkSqlInvokeCommand command) {
        SqlInvokeCommand sqlInvokeCommand = new SqlInvokeCommand();
        sqlInvokeCommand.setParams(command.getParams());
        sqlInvokeCommand.setHeader(command.getHeader());
        sqlInvokeCommand.setDatabase(command.getDatabase());
        sqlInvokeCommand.setTable(command.getTable());
        sqlInvokeCommand.setType(QuerySqlTypeEnum.findByName(command.getType()));
        sqlInvokeCommand.setUpdateItems(command.getUpdateItems());
        NodeInvokeResult nodeInvokeResult = service.invokeCallNode(sqlInvokeCommand);
        return nodeInvokeResult.getResult();
    }

}
