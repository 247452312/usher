package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.NodeAssembler;
import top.uhyils.usher.pojo.DO.NodeDO;
import top.uhyils.usher.pojo.DTO.NodeDTO;
import top.uhyils.usher.pojo.entity.Node;
import top.uhyils.usher.repository.NodeRepository;
import top.uhyils.usher.service.NodeService;

/**
 * 转换节点表(Node)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Service
@ReadWriteMark(tables = {"sys_node"})
public class NodeServiceImpl extends AbstractDoService<NodeDO, Node, NodeDTO, NodeRepository, NodeAssembler> implements NodeService {

    public NodeServiceImpl(NodeAssembler assembler, NodeRepository repository) {
        super(assembler, repository);
    }


}
