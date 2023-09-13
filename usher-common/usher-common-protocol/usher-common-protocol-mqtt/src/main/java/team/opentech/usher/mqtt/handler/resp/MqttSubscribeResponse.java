package team.opentech.usher.mqtt.handler.resp;

import java.io.Serializable;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月11日 20时07分
 */
public class MqttSubscribeResponse implements Serializable {

    private Boolean success;

    private Integer qos;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }
}
