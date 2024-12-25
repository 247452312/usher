package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.CallNodeDTO;
import top.uhyils.usher.protocol.rpc.CallNodeProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.CallNodeService;

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

