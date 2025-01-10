package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.NetNodeInfoDetailDO;
import top.uhyils.usher.pojo.entity.NetNodeInfoDetail;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 网络调用独立可工作节点支持的语句类型(NetNodeInfoDetail)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
public interface NetNodeInfoDetailRepository extends BaseEntityRepository<NetNodeInfoDetailDO, NetNodeInfoDetail> {

    /**
     * 根据nodeid获取详情
     *
     * @param unique
     *
     * @return
     */
    List<NetNodeInfoDetail> findByNodeId(Long unique);
}
