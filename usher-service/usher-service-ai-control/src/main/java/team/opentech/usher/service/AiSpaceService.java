package team.opentech.usher.service;


import team.opentech.usher.pojo.DTO.AiSpaceDTO;
import team.opentech.usher.pojo.cqe.AddUserToSpaceCommand;
import team.opentech.usher.pojo.cqe.RemoveSpaceCommand;
import team.opentech.usher.pojo.cqe.RemoveUserFromSpaceCommand;
import team.opentech.usher.pojo.cqe.SpaceCreateCommand;
import team.opentech.usher.pojo.event.CleanSpaceUserEvent;

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
}
