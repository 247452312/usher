package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.CallNodeDTO;
import team.opentech.usher.protocol.rpc.CallNodeProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.CallNodeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 调用节点表, 真正调用的节点(CallNode)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@RpcService
public class CallNodeProviderImpl extends BaseDefaultProvider<CallNodeDTO> implements CallNodeProvider {


    @Autowired
    private CallNodeService service;


    @Override
    protected BaseDoService<CallNodeDTO> getService() {
        return service;
    }

}

