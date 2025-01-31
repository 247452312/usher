package team.opentech.usher.pojo.entity.type;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月25日 09时15分
 */
public class LoginInfo implements BaseType {

    private UserName username;

    private Password password;

    public LoginInfo(UserName username, Password password) {
        this.username = username;
        this.password = password;
    }

    public UserName getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }
}
