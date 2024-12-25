package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.pojo.cqe.InvokeCommand;
import top.uhyils.usher.protocol.rpc.GatewaySdkProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.GatewaySdkService;

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
