package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.NetNodeInfoDO;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.entity.NetNodeInfo;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 网络调用独立可工作节点(NetNodeInfo)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2025年01月10日 15时22分
 */
public interface NetNodeInfoRepository extends BaseEntityRepository<NetNodeInfoDO, NetNodeInfo> {


    /**
     * @param database
     * @param table
     *
     * @return
     */
    NetNodeInfo findNodeByDatabaseAndTable(Long companyId, String database, String table);


    /**
     * 根据公司id和库名查询节点
     *
     * @param companyId
     * @param databases
     *
     * @return
     */
    List<NetNodeInfo> findByCompanyIdAndDatabase(Long companyId, List<String> databases);

    /**
     * 根据公司id查询所有节点
     */
    List<NetNodeInfo> findByCompanyId(IdCommand idCommand);
}
