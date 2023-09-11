package team.opentech.usher.mqtt.pojo.resp;

/**
 * mqtt响应
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月07日 09时34分
 */
public interface MqttResponse {

    /**
     * 响应转协议
     *
     * @return
     */
    byte[] toBytes();
}
