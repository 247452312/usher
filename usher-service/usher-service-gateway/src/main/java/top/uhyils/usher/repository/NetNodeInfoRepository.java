package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.pojo.DO.NetNodeInfoDO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.CallNodeQuery;
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
     * 获取此人有权限的库
     *
     * @param userDTO
     *
     * @return
     */
    List<NetNodeInfo> findByUser(UserDTO userDTO);

    /**
     * @param database
     * @param table
     *
     * @return
     */
    @NotNull
    NetNodeInfo findNodeByDatabaseAndTable(String database, String table);


    /**
     * 判断要查询的是否是系统表
     *
     * @param database
     *
     * @return
     */
    Boolean judgeSysTable(String database);


    /**
     * 查询调用节点
     *
     * @param userId
     * @param callNodeQuery
     *
     * @return
     */
    List<NetNodeInfo> query(Long userId, CallNodeQuery callNodeQuery);

    /**
     * 根据公司id和库名查询节点
     *
     * @param companyId
     * @param database
     *
     * @return
     */
    List<NetNodeInfo> findByCompanyIdAndDatabase(Long companyId, String database);
}
