package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import javax.annotation.Resource;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.NodeAssembler;
import team.opentech.usher.dao.NodeDao;
import team.opentech.usher.pojo.DO.NodeDO;
import team.opentech.usher.pojo.DTO.NodeDTO;
import team.opentech.usher.pojo.entity.AbstractDataNode;
import team.opentech.usher.pojo.entity.Node;
import team.opentech.usher.repository.NodeRepository;
import team.opentech.usher.repository.ProviderInterfaceRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 转换节点表(Node)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Repository
public class NodeRepositoryImpl extends AbstractRepository<Node, NodeDO, NodeDao, NodeDTO, NodeAssembler> implements NodeRepository {

    @Resource
    private ProviderInterfaceRepository providerInterfaceRepository;


    protected NodeRepositoryImpl(NodeAssembler convert, NodeDao dao) {
        super(convert, dao);
    }


    @Override
    public AbstractDataNode findNodeOrProvider(String database, String table) {

        LambdaQueryWrapper<NodeDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(NodeDO::getDatabase, database);
        queryWrapper.eq(NodeDO::getTableName, table);

        NodeDO nodeDO = dao.selectOne(queryWrapper);
        if (nodeDO != null) {
            return assembler.toEntity(nodeDO);
        }
        return providerInterfaceRepository.find(database, table);
    }
}
