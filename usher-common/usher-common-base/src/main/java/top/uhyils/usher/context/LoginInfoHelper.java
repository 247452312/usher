package top.uhyils.usher.context;

import java.util.Optional;
import top.uhyils.usher.UsherThreadLocal;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.util.DefaultCQEBuildUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月22日 16时27分
 */
public class LoginInfoHelper {

    /**
     * mysql协议的角色id
     */
    public static final Long MYSQL_ROLE_ID = -1L;

    /**
     * 用户在rpc的header中的用户ip的key
     */
    public static final String USER_IP_RPC_KEY = "user_ip";

    private static final UsherThreadLocal<UserDTO> USER = new UsherThreadLocal<>();

    private static final UsherThreadLocal<String> TOKEN = new UsherThreadLocal<>();

    private static final UsherThreadLocal<String> IP = new UsherThreadLocal<>();

    public static UserDTO setUser(UserDTO userDO) {
        UserDTO lastUser = USER.get();
        USER.set(userDO);
        return lastUser;
    }

    public static UserDTO clean() {
        UserDTO userDO = USER.get();
        USER.remove();
        TOKEN.remove();
        cleanIp();
        return userDO;
    }

    public static void cleanIp() {
        IP.remove();
    }

    public static Optional<UserDTO> get() {
        return Optional.ofNullable(USER.get());
    }

    public static Optional<String> getToken() {
        return Optional.ofNullable(TOKEN.get());
    }

    public static void setToken(String token) {
        TOKEN.set(token);
    }

    public static void setIp(String ip) {
        IP.set(ip);
    }

    /**
     * 制作一个cqe来用
     *
     * @return
     */
    public static DefaultCQE makeCQE() {
        UserDTO userDTO = doGet();
        String token = getToken().orElse(null);
        DefaultCQE defaultCQE = new DefaultCQE();
        defaultCQE.setUser(userDTO);
        defaultCQE.setToken(token);
        return defaultCQE;
    }

    /**
     * 填充一个cqe来用
     *
     * @return
     */
    public static <T extends DefaultCQE> T fillCQE(T request) {
        UserDTO userDTO = doGet();
        String token = getToken().orElse(null);
        request.setUser(userDTO);
        request.setToken(token);
        return request;
    }

    public static UserDTO doGet() {
        UserDTO userDO = USER.get();
        if (userDO == null) {
            USER.set(DefaultCQEBuildUtil.getAdminUserDTO());
        }
        return USER.get();
    }

    public static Optional<String> getUserIp() {
        return Optional.ofNullable(IP.get());
    }
}
