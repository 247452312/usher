package team.opentech.usher.facade;

import java.util.List;
import team.opentech.usher.pojo.DTO.UserDTO;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月04日 08时51分
 */
public interface UserFacade extends BaseFacade {


    /**
     * 根据用户id获取用户
     *
     * @param userId
     *
     * @return
     */
    UserDTO getById(Long userId);

    /**
     * 批量根据id获取用户
     *
     * @param userIds
     *
     * @return
     */
    List<UserDTO> getByIds(List<Long> userIds);

}
