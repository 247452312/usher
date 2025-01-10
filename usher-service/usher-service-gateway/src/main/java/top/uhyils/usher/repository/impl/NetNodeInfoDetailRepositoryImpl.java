package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.NetNodeInfoDetailAssembler;
import top.uhyils.usher.dao.NetNodeInfoDetailDao;
import top.uhyils.usher.pojo.DO.NetNodeInfoDetailDO;
import top.uhyils.usher.pojo.DTO.NetNodeInfoDetailDTO;
import top.uhyils.usher.pojo.entity.NetNodeInfoDetail;
import top.uhyils.usher.repository.NetNodeInfoDetailRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 网络调用独立可工作节点支持的语句类型(NetNodeInfoDetail)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
@Repository
public class NetNodeInfoDetailRepositoryImpl extends AbstractRepository<NetNodeInfoDetail, NetNodeInfoDetailDO, NetNodeInfoDetailDao, NetNodeInfoDetailDTO, NetNodeInfoDetailAssembler> implements NetNodeInfoDetailRepository {

    protected NetNodeInfoDetailRepositoryImpl(NetNodeInfoDetailAssembler convert, NetNodeInfoDetailDao dao) {
        super(convert, dao);
    }


    @Override
    public List<NetNodeInfoDetail> findByNodeId(Long unique) {
        LambdaQueryChainWrapper<NetNodeInfoDetailDO> wrapper = lambdaQuery();
        wrapper.eq(NetNodeInfoDetailDO::getNodeId, unique);
        List<NetNodeInfoDetailDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }
}
