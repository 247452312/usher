package team.opentech.usher.repository;

import java.util.List;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.enums.InvokeTypeEnum;
import team.opentech.usher.pojo.DO.CallNodeDO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.CallNodeQuery;
import team.opentech.usher.pojo.entity.CallNode;
import team.opentech.usher.repository.base.BaseEntityRepository;

/**
 * 调用节点表, 真正调用的节点(CallNode)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public interface CallNodeRepository extends BaseEntityRepository<CallNodeDO, CallNode> {

    /**
     * 获取此人有权限的库
     *
     * @param userDTO
     *
     * @return
     */
    List<CallNode> findByUser(UserDTO userDTO);

    /**
     * @param database
     * @param table
     *
     * @return
     */
    @NotNull
    CallNode findNodeByDatabaseAndTable(String database, String table, InvokeTypeEnum invokeType);


    /**
     * 判断要查询的是否是系统表
     *
     * @param path
     *
     * @return
     */
    Boolean judgeSysTable(String path);


    /**
     * 查询调用节点
     *
     * @param userId
     * @param callNodeQuery
     *
     * @return
     */
    List<CallNode> query(Long userId, CallNodeQuery callNodeQuery);
}
