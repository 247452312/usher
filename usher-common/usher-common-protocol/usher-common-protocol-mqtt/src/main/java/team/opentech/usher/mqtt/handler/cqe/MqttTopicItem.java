package team.opentech.usher.mqtt.handler.cqe;

import java.io.Serializable;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时50分
 */
public class MqttTopicItem implements Serializable {

    private String topic;

    private Integer qos;

    /**
     * 快捷创建
     */
    public static MqttTopicItem build(String topic, Integer qos) {
        MqttTopicItem build = new MqttTopicItem();
        build.setTopic(topic);
        build.setQos(qos);
        return build;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }
}
