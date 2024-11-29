package team.opentech.usher.pojo.entity;

import java.util.Map;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.pojo.entity.DataNode;
import team.opentech.usher.pojo.DO.base.BaseDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.repository.NodeRepository;
import team.opentech.usher.repository.ProviderInterfaceRepository;

/**
 * 数据处理节点模板
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月30日 09时12分
 */
public abstract class AbstractDataNode<T extends BaseDO> extends AbstractDoEntity<T> implements DataNode {


    protected AbstractDataNode(T t) {
        super(t);
    }

    protected AbstractDataNode(Long id, T t) {
        super(id, t);
    }

    @Override
    public NodeInvokeResult getResult(Map<String, String> header, Map<String, Object> params) {
        return new NodeInvokeResult(null);
    }

    /**
     * 填充必要数据
     *
     * @param nodeRepository
     * @param providerInterfaceRepository
     */
    public abstract void fill(NodeRepository nodeRepository, ProviderInterfaceRepository providerInterfaceRepository);

}
