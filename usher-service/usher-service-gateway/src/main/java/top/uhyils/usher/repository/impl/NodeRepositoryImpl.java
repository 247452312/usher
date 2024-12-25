package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.NodeAssembler;
import top.uhyils.usher.dao.NodeDao;
import top.uhyils.usher.pojo.DO.NodeDO;
import top.uhyils.usher.pojo.DTO.NodeDTO;
import top.uhyils.usher.pojo.entity.AbstractDataNode;
import top.uhyils.usher.pojo.entity.Node;
import top.uhyils.usher.repository.NodeRepository;
import top.uhyils.usher.repository.ProviderInterfaceRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 转换节点表(Node)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Repository
public class NodeRepositoryImpl extends AbstractRepository<Node, NodeDO, NodeDao, NodeDTO, NodeAssembler> implements NodeRepository {

    @Autowired
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
