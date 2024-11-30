package top.uhyils.usher.pojo.DTO.request;

import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月08日 19时46分
 */
public class ApplyUserCommand extends AbstractCommand {

    /**
     * 要申请的用户
     */
    private UserDTO userDTO;


    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
