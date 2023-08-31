package team.opentech.usher.protocol.rpc.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.pojo.cqe.InvokeCommand;
import team.opentech.usher.protocol.rpc.GatewaySdkProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.GatewaySdkService;

/**
 * 对外 rpc协议
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月12日 09时06分
 */
@RpcService
public class GatewaySdkProviderImpl implements GatewaySdkProvider {

    @Resource
    private GatewaySdkService service;

    @Override
    public List<Map<String, Object>> invokeRpc(InvokeCommand command) {
        NodeInvokeResult nodeInvokeResult = service.invokeInterface(command);
        return nodeInvokeResult.getResult();
    }

}
