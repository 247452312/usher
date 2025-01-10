package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;
import top.uhyils.usher.protocol.rpc.NetNodeInfoDetailProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.NetNodeInfoDetailService;

/**
 * 网络调用独立可工作节点支持的语句类型(NetNodeInfoDetail)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@RpcService
public class NetNodeInfoDetailProviderImpl extends BaseDefaultProvider<NetNodeInfoDetailDTO> implements NetNodeInfoDetailProvider {


    @Autowired
    private NetNodeInfoDetailService service;


    @Override
    protected BaseDoService<NetNodeInfoDetailDTO> getService() {
        return service;
    }

}

