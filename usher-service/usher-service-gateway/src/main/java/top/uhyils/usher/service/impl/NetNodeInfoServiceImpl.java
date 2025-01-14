package top.uhyils.usher.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.NetNodeInfoAssembler;
import top.uhyils.usher.assembler.NetNodeInfoDetailAssembler;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.pojo.DO.NetNodeInfoDO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;
import top.uhyils.usher.pojo.DTO.base.IdDTO;
import top.uhyils.usher.pojo.cqe.NetNodeCreateCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.entity.NetNodeInfo;
import top.uhyils.usher.pojo.entity.NetNodeInfoDetail;
import top.uhyils.usher.repository.NetNodeInfoDetailRepository;
import top.uhyils.usher.repository.NetNodeInfoRepository;
import top.uhyils.usher.service.NetNodeInfoService;
import top.uhyils.usher.util.Asserts;

/**
 * 网络调用独立可工作节点(NetNodeInfo)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@Service
@ReadWriteMark(tables = {"sys_net_node_info"})
public class NetNodeInfoServiceImpl extends AbstractDoService<NetNodeInfoDO, NetNodeInfo, NetNodeInfoDTO, NetNodeInfoRepository, NetNodeInfoAssembler> implements NetNodeInfoService {

    @Resource
    private NetNodeInfoDetailRepository netNodeInfoDetailRepository;

    @Resource
    private NetNodeInfoDetailAssembler netNodeInfoDetailAssembler;


    @Resource
    private NetNodeInfoDetailAssembler detailAssembler;

    public NetNodeInfoServiceImpl(NetNodeInfoAssembler assembler, NetNodeInfoRepository repository) {
        super(assembler, repository);
    }

    @Override
    public NetNodeInfoDTO findByDatabaseAndTable(String database, String table) {
        Long companyId = LoginInfoHelper.get().map(IdDTO::getId).orElse(null);
        Asserts.assertTrue(companyId != null, "用户未登录");
        NetNodeInfo nodeInfo = rep.findNodeByDatabaseAndTable(companyId, database, table);
        return assem.toDTO(nodeInfo);
    }

    @Override
    public List<NetNodeInfoDetailDTO> findDetailById(Long id) {
        List<NetNodeInfoDetail> byNodeId = netNodeInfoDetailRepository.findByNodeId(id);
        return netNodeInfoDetailAssembler.listEntityToDTO(byNodeId);
    }

    @Override
    public List<NetNodeInfoDTO> findWithDetailByCompanyId(IdCommand idCommand) {
        List<NetNodeInfo> result = rep.findByCompanyId(idCommand);
        return assem.listEntityToDTOWithDetail(result);
    }

    @Override
    public List<NetNodeInfoDTO> findByCompanyIdAndDatabase(Long companyId, List<String> databases) {
        List<NetNodeInfo> nodeDTOs = rep.findByCompanyIdAndDatabase(companyId, databases);
        return assem.listEntityToDTO(nodeDTOs);
    }

    @Override
    public Boolean add(NetNodeCreateCommand command) {
        NetNodeInfo nodeInfo = assem.toEntity(command);
        List<NetNodeInfoDetail> details = detailAssembler.toEntity(command);
        nodeInfo.saveSelfByCompanyId(rep, details, command.getCompanyId());
        return Boolean.TRUE;
    }

}
