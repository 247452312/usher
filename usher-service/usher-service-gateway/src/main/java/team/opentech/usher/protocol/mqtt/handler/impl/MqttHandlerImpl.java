package team.opentech.usher.protocol.mqtt.handler.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
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
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月14日 12时49分
 */
@Service
public class MqttHandlerImpl implements MqttServiceHandler {

    @Override
    public MqttLoginResponse login(MqttLoginCommand loginCommand) {
        return null;
    }

    @Override
    public List<MqttSubscribeResponse> subscribe(MqttSubscribeTopicCommand mqttSubscribeTopicCommand) {
        return null;
    }

    @Override
    public MqttPublishResponse publish(MqttPublishCommand mqttPublishCommand) {
        return null;
    }

    @Override
    public void pubCck(MqttPubAckCommand mqttPubAckCommand) {

    }

    @Override
    public MqttPubRecResponse pubRec(MqttPubRecCommand build) {
        return null;
    }

    @Override
    public MqttPubRelResponse pubRel(MqttPubRelCommand build) {
        return null;
    }

    @Override
    public void pubComp(MqttPubCompCommand build) {

    }

    @Override
    public void logout(MqttLogoutCommand build) {

    }

    @Override
    public List<MqttUnSubscribeResponse> unSubscribe(MqttUnSubscribeCommand mqttUnSubscribeCommand) {
        return null;
    }
}
