package team.opentech.usher.mqtt.handler.cqe;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时42分
 */
public class MqttLogoutCommand extends AbstractCommand {

    /**
     * 客户端id
     */
    private String clientIdentifier;

    /**
     * 快捷创建
     */
    public static MqttLogoutCommand build(String clientIdentifier) {
        MqttLogoutCommand build = new MqttLogoutCommand();
        build.setClientIdentifier(clientIdentifier);
        return build;
    }

    public String getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }
}
