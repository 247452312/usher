package top.uhyils.usher.service;


import java.util.List;
import top.uhyils.usher.pojo.DTO.AiSpaceDTO;
import top.uhyils.usher.pojo.DTO.AiSubspaceDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.AddUserToSpaceCommand;
import top.uhyils.usher.pojo.cqe.ChangeSubSpaceCommand;
import top.uhyils.usher.pojo.cqe.CreateSubSpaceCommand;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.FindSubSpaceBySpaceIdQuery;
import top.uhyils.usher.pojo.cqe.RemoveSpaceCommand;
import top.uhyils.usher.pojo.cqe.RemoveUserFromSpaceCommand;
import top.uhyils.usher.pojo.cqe.SpaceCreateCommand;
import top.uhyils.usher.pojo.cqe.UpdateSpaceInfoCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.pojo.event.CleanSpaceUserEvent;

/**
 * 独立空间(AiSpace)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public interface AiSpaceService extends BaseDoService<AiSpaceDTO> {

    /**
     * 创建一个新的空间
     *
     * @return 是否创建成功
     */
    Boolean create(SpaceCreateCommand command);


    /**
     * 添加一个用户到空间
     *
     * @return 是否添加成功
     */
    Boolean addUserToSpace(AddUserToSpaceCommand command);

    /**
     * 从空间中移除一个用户
     *
     * @return 是否移除成功
     */
    Boolean removeUserFromSpace(RemoveUserFromSpaceCommand command);


    /**
     * 创建一个子空间
     *
     * @param command
     *
     * @return
     */
    Boolean createSubSpace(CreateSubSpaceCommand command);

    /**
     * 移除空间
     *
     * @return 是否移除成功
     */
    Boolean removeSpace(RemoveSpaceCommand command);

    /**
     * 清空用户
     *
     * @param event
     */
    void cleanSpaceUserEvent(CleanSpaceUserEvent event);

    /**
     * 查询当前账号下的所有独立空间
     *
     * @param blackQuery
     *
     * @return
     */
    List<AiSpaceDTO> findByOnlineUser(DefaultCQE blackQuery);


    /**
     * 根据空间id查询下面的所有用户
     *
     * @param query
     *
     * @return
     */
    List<UserDTO> findUserBySpaceId(IdQuery query);

    /**
     * 删除一个子空间
     *
     * @param command
     *
     * @return
     */
    Boolean removeSubSpace(IdCommand command);

    /**
     * 修改一个子空间
     *
     * @param command
     *
     * @return
     */
    Boolean changeSubSpace(ChangeSubSpaceCommand command);

    /**
     * 获取某个子空间
     *
     * @param query
     *
     * @return
     */
    AiSubspaceDTO findSubSpaceById(IdQuery query);

    /**
     * 根据独立空间id获取独立空间下所有子空间
     *
     * @param query
     *
     * @return
     */
    List<AiSubspaceDTO> findSubSpaceBySpaceId(FindSubSpaceBySpaceIdQuery query);


    /**
     * 更新空间信息
     *
     * @param command
     *
     * @return
     */
    Boolean updateSpaceInfo(UpdateSpaceInfoCommand command);
}
