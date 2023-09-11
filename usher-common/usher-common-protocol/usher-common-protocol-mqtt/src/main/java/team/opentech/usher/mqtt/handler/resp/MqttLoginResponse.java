package team.opentech.usher.mqtt.handler.resp;

import java.io.Serializable;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月11日 14时29分
 */
public class MqttLoginResponse implements Serializable {

    private Boolean success;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
