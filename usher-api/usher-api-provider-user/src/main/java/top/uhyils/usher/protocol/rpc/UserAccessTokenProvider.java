package top.uhyils.usher.protocol.rpc;

import java.util.List;
import top.uhyils.usher.pojo.DTO.LoginDTO;
import top.uhyils.usher.pojo.DTO.UserAccessTokenDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.RandomCreateTokenCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.command.StringCommand;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * 用户对应令牌(UserAccessToken)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月18日 16时09分
 */
public interface UserAccessTokenProvider extends DTOProvider<UserAccessTokenDTO> {

    /**
     * 随机创建一个令牌
     *
     * @param command
     *
     * @return
     */
    UserAccessTokenDTO randomCreateToken(RandomCreateTokenCommand command);


    /**
     * 删除令牌
     *
     * @param command
     *
     * @return
     */
    Boolean deleteToken(IdCommand command);

    /**
     * 获取当前用户所有令牌 不会返回具体令牌值
     *
     * @param defaultCQE
     *
     * @return
     */
    List<UserAccessTokenDTO> findAllToken(DefaultCQE defaultCQE);

    /**
     * 校验token是否有效,如果无效则返回null
     *
     * @param command
     *
     * @return
     */
    LoginDTO accessToken(StringCommand command);

    /**
     * 校验token是否有效
     *
     * @param command
     *
     * @return
     */
    Boolean checkAccessToken(StringCommand command);

}
