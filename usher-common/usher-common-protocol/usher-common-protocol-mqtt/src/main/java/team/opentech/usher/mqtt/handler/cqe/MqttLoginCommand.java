package team.opentech.usher.mqtt.handler.cqe;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * 登录command
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月11日 14时28分
 */
public class MqttLoginCommand extends AbstractCommand {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 快捷创建
     */
    public static MqttLoginCommand build(String username, String password) {
        MqttLoginCommand build = new MqttLoginCommand();
        build.username = username;
        build.password = password;
        return build;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
