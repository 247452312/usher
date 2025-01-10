package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.protocol.rpc.NetNodeInfoProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.NetNodeInfoService;

/**
 * 网络调用独立可工作节点(NetNodeInfo)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@RpcService
public class NetNodeInfoProviderImpl extends BaseDefaultProvider<NetNodeInfoDTO> implements NetNodeInfoProvider {


    @Autowired
    private NetNodeInfoService service;


    @Override
    protected BaseDoService<NetNodeInfoDTO> getService() {
        return service;
    }

}

