package team.opentech.usher.repository;

import java.util.List;
import team.opentech.usher.pojo.DO.AiSpaceUserLinkDO;
import team.opentech.usher.pojo.entity.AiSpaceUserLink;
import team.opentech.usher.repository.base.BaseEntityRepository;

/**
 * 独立空间-用户关联表(AiSpaceUserLink)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public interface AiSpaceUserLinkRepository extends BaseEntityRepository<AiSpaceUserLinkDO, AiSpaceUserLink> {

    /**
     * 检查某个空间是否还有管理员
     *
     * @param spaceId
     *
     * @return
     */
    boolean haveAdmin(Long spaceId);

    /**
     * 检查用户是否存在
     *
     * @param userId
     * @param spaceId
     *
     * @return
     */
    boolean checkUsher(Long userId, Long spaceId);

    /**
     * 移除用户
     *
     * @param id
     * @param userId
     *
     * @return
     */
    Boolean removeUser(Long id, Long userId);

    /**
     * 检查是否是管理员
     *
     * @param userId
     * @param spaceId
     *
     * @return
     */
    boolean isAdmin(Long userId, Long spaceId);

    /**
     * 根据独立空间id清空所有用户
     *
     * @param spaceId
     */
    void removeBySpaceId(Long spaceId);

    /**
     * 根据用户id获取与独立空间的关联
     *
     * @param usherId
     *
     * @return
     */
    List<AiSpaceUserLink> findByUserId(Long usherId);

    /**
     * 根据空间id获取所有用户
     *
     * @param spaceId
     *
     * @return
     */
    List<AiSpaceUserLink> findBySpaceId(Long spaceId);
}
