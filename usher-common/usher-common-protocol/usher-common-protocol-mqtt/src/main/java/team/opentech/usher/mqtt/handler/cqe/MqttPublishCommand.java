package team.opentech.usher.mqtt.handler.cqe;

import team.opentech.usher.mqtt.netty.callback.MqttCallback;
import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * mqtt 发布消息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月12日 09时28分
 */
public class MqttPublishCommand extends AbstractCommand {


    private Integer qos;

    private String topic;

    private String body;

    private MqttCallback callback;

    /**
     * 快捷创建
     */
    public static MqttPublishCommand build(Integer qos, String topic, String body, MqttCallback callback) {
        MqttPublishCommand build = new MqttPublishCommand();
        build.qos = qos;
        build.topic = topic;
        build.body = body;
        build.callback = callback;
        return build;
    }


    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
