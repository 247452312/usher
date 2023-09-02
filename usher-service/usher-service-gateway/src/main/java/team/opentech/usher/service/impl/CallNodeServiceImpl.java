package team.opentech.usher.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.CallNodeAssembler;
import team.opentech.usher.pojo.DO.CallNodeDO;
import team.opentech.usher.pojo.DTO.CallNodeDTO;
import team.opentech.usher.pojo.cqe.query.demo.Arg;
import team.opentech.usher.pojo.entity.CallNode;
import team.opentech.usher.repository.CallNodeRepository;
import team.opentech.usher.repository.NodeRepository;
import team.opentech.usher.repository.ProviderInterfaceRepository;
import team.opentech.usher.service.CallNodeService;

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
