package top.uhyils.usher.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.NetNodeInfoAssembler;
import top.uhyils.usher.assembler.NetNodeInfoDetailAssembler;
import top.uhyils.usher.pojo.DO.NetNodeInfoDO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDTO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;
import top.uhyils.usher.pojo.entity.NetNodeInfo;
import top.uhyils.usher.pojo.entity.NetNodeInfoDetail;
import top.uhyils.usher.repository.NetNodeInfoDetailRepository;
import top.uhyils.usher.repository.NetNodeInfoRepository;
import top.uhyils.usher.service.NetNodeInfoService;

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

    public NetNodeInfoServiceImpl(NetNodeInfoAssembler assembler, NetNodeInfoRepository repository) {
        super(assembler, repository);
    }

    @Override
    public NetNodeInfoDTO findByDatabaseAndTable(String database, String table) {
        NetNodeInfo nodeInfo = rep.findNodeByDatabaseAndTable(database, table);
        return assem.toDTO(nodeInfo);
    }

    @Override
    public List<NetNodeInfoDetailDTO> findDetailById(Long id) {
        List<NetNodeInfoDetail> byNodeId = netNodeInfoDetailRepository.findByNodeId(id);
        return netNodeInfoDetailAssembler.listEntityToDTO(byNodeId);
    }
}
