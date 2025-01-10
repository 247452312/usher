package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.NetNodeInfoDetailAssembler;
import top.uhyils.usher.pojo.DO.NetNodeInfoDetailDO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;
import top.uhyils.usher.pojo.entity.NetNodeInfoDetail;
import top.uhyils.usher.repository.NetNodeInfoDetailRepository;
import top.uhyils.usher.service.NetNodeInfoDetailService;

/**
 * 网络调用独立可工作节点支持的语句类型(NetNodeInfoDetail)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@Service
@ReadWriteMark(tables = {"sys_net_node_info_detail"})
public class NetNodeInfoDetailServiceImpl extends AbstractDoService<NetNodeInfoDetailDO, NetNodeInfoDetail, NetNodeInfoDetailDTO, NetNodeInfoDetailRepository, NetNodeInfoDetailAssembler> implements NetNodeInfoDetailService {

    public NetNodeInfoDetailServiceImpl(NetNodeInfoDetailAssembler assembler, NetNodeInfoDetailRepository repository) {
        super(assembler, repository);
    }


}
