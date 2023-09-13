package team.opentech.usher.mqtt.handler;

import java.util.List;
import team.opentech.usher.mqtt.handler.cqe.MqttLoginCommand;
import team.opentech.usher.mqtt.handler.cqe.MqttLogoutCommand;
import team.opentech.usher.mqtt.handler.cqe.MqttPubAckCommand;
import team.opentech.usher.mqtt.handler.cqe.MqttPubCompCommand;
import team.opentech.usher.mqtt.handler.cqe.MqttPubRecCommand;
import team.opentech.usher.mqtt.handler.cqe.MqttPubRelCommand;
import team.opentech.usher.mqtt.handler.cqe.MqttPublishCommand;
import team.opentech.usher.mqtt.handler.cqe.MqttSubscribeTopicCommand;
import team.opentech.usher.mqtt.handler.cqe.MqttUnSubscribeCommand;
import team.opentech.usher.mqtt.handler.resp.MqttLoginResponse;
import team.opentech.usher.mqtt.handler.resp.MqttPubRecResponse;
import team.opentech.usher.mqtt.handler.resp.MqttPubRelResponse;
import team.opentech.usher.mqtt.handler.resp.MqttPublishResponse;
import team.opentech.usher.mqtt.handler.resp.MqttSubscribeResponse;
import team.opentech.usher.mqtt.handler.resp.MqttUnSubscribeResponse;

/**
 * 需要子类实现的接口SPI
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月07日 09时24分
 */
public interface MqttServiceHandler {

    /**
     * mqtt连接登录
     */
    MqttLoginResponse login(MqttLoginCommand loginCommand);

    /**
     * 订阅
     */
    List<MqttSubscribeResponse> subscribe(MqttSubscribeTopicCommand mqttSubscribeTopicCommand);

    /**
     * 发布消息
     */
    MqttPublishResponse publish(MqttPublishCommand mqttPublishCommand);

    /**
     * 发布消息接收确认完成
     *
     * @param mqttPubAckCommand
     */
    void pubCck(MqttPubAckCommand mqttPubAckCommand);

    /**
     * QOS2 第一阶段 消息已接收
     *
     * @param build
     *
     * @return
     */
    MqttPubRecResponse pubRec(MqttPubRecCommand build);

    /**
     * QOS2 第二阶段 消息释放消息
     *
     * @param build
     *
     * @return
     */
    MqttPubRelResponse pubRel(MqttPubRelCommand build);

    /**
     * QOS2 第三阶段 发布结束
     *
     * @param build
     */
    void pubComp(MqttPubCompCommand build);

    /**
     * 登出
     *
     * @param build
     */
    void logout(MqttLogoutCommand build);

    /**
     * 取消订阅消息
     *
     * @param mqttUnSubscribeCommand
     *
     * @return
     */
    List<MqttUnSubscribeResponse> unSubscribe(MqttUnSubscribeCommand mqttUnSubscribeCommand);
}
