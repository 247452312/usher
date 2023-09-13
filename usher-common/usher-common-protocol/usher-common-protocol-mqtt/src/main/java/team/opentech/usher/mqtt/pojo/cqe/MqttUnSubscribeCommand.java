package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.handler.cqe.MqttTopicItem;
import team.opentech.usher.mqtt.handler.resp.MqttUnSubscribeResponse;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.mqtt.pojo.resp.MqttUnSubAckResponse;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时43分
 */
public class MqttUnSubscribeCommand extends AbstractMqttCommand {


    /**
     * 字典项
     */
    private List<MqttTopicItem> items;


    /**
     * 取消发布唯一标示, 回应要带着的
     */
    private Integer packetIdentifier;

    public MqttUnSubscribeCommand(byte[] bytes) {
        super(bytes);
    }

    @Override
    public byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler) {
        team.opentech.usher.mqtt.handler.cqe.MqttUnSubscribeCommand mqttUnSubscribeCommand = new team.opentech.usher.mqtt.handler.cqe.MqttUnSubscribeCommand();
        mqttUnSubscribeCommand.setItems(items);
        List<MqttUnSubscribeResponse> resp = mqttServiceHandler.unSubscribe(mqttUnSubscribeCommand);
        return new MqttUnSubAckResponse(packetIdentifier).toBytes();
    }

    @Override
    protected void loadVariableHeader(ByteBuf buf) {
        this.packetIdentifier = loadLastInt(buf);
    }

    @Override
    protected void loadPlayLoad(ByteBuf buf) {
        items = new ArrayList<>();
        while (Objects.equals(buf.readerIndex(), buf.writerIndex())) {
            String topic = loadLastStringItem(buf);
            Integer qos = (buf.readByte() & 0x1);
            items.add(MqttTopicItem.build(topic, qos));
        }
    }
}
