package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.NodeAssembler;
import team.opentech.usher.pojo.DO.NodeDO;
import team.opentech.usher.pojo.DTO.NodeDTO;
import team.opentech.usher.pojo.entity.Node;
import team.opentech.usher.repository.NodeRepository;
import team.opentech.usher.service.NodeService;
import org.springframework.stereotype.Service;

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
