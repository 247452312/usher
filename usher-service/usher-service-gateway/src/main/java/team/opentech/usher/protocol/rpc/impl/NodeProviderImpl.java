package team.opentech.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.NodeDTO;
import team.opentech.usher.protocol.rpc.NodeProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.NodeService;

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

