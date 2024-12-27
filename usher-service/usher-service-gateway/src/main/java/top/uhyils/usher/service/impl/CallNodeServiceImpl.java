package top.uhyils.usher.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.CallNodeAssembler;
import top.uhyils.usher.pojo.DO.CallNodeDO;
import top.uhyils.usher.pojo.DTO.CallNodeDTO;
import top.uhyils.usher.pojo.cqe.query.demo.Arg;
import top.uhyils.usher.pojo.entity.CallNode;
import top.uhyils.usher.repository.CallNodeRepository;
import top.uhyils.usher.repository.NodeRepository;
import top.uhyils.usher.repository.ProviderInterfaceRepository;
import top.uhyils.usher.service.CallNodeService;

/**
 * 调用节点表, 真正调用的节点(CallNode)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Service
@ReadWriteMark(tables = {"sys_call_node"})
public class CallNodeServiceImpl extends AbstractDoService<CallNodeDO, CallNode, CallNodeDTO, CallNodeRepository, CallNodeAssembler> implements CallNodeService {


    @Resource
    private NodeRepository nodeRepository;

    @Resource
    private ProviderInterfaceRepository providerInterfaceRepository;

    public CallNodeServiceImpl(CallNodeAssembler assembler, CallNodeRepository repository) {
        super(assembler, repository);
    }


    @Override
    public List<CallNodeDTO> queryWithAllNode(List<Arg> args) {
        List<CallNode> callNodes = rep.findNoPage(args);
        for (CallNode callNode : callNodes) {
            // 向下填充
            callNode.fill(nodeRepository, providerInterfaceRepository);
        }
        return assem.listEntityToDTO(callNodes);
    }
}
