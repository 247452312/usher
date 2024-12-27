package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.NodeDTO;
import top.uhyils.usher.protocol.rpc.NodeProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.NodeService;

/**
 * 转换节点表(Node)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@RpcService
public class NodeProviderImpl extends BaseDefaultProvider<NodeDTO> implements NodeProvider {


    @Autowired
    private NodeService service;


    @Override
    protected BaseDoService<NodeDTO> getService() {
        return service;
    }

}

