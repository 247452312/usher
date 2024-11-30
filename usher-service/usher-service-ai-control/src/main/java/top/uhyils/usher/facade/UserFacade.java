package top.uhyils.usher.facade;

import java.util.List;
import top.uhyils.usher.pojo.DTO.UserDTO;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月18日 14时46分
 */
public interface UserFacade extends BaseFacade {


    /**
     * 根据用户id获取用户信息
     *
     * @param userIds
     *
     * @return
     */
    List<UserDTO> findByUserIdList(List<Long> userIds);


}
