package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.handler.cqe.MqttSubscribeTopicCommand;
import team.opentech.usher.mqtt.handler.cqe.MqttTopicItem;
import team.opentech.usher.mqtt.handler.resp.MqttSubscribeResponse;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.mqtt.pojo.resp.MqttSubAckResponse;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月11日 19时03分
 */
public class MqttSubscribeCommand extends AbstractMqttCommand {

    /**
     * 字典项
     */
    private List<MqttTopicItem> items;



    /**
     * 发布唯一标示, 回应要带着的
     */
    private Integer packetIdentifier;

    public MqttSubscribeCommand(byte[] bytes) {
        super(bytes);
    }


    @Override
    public byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler) {
        MqttSubscribeTopicCommand mqttSubscribeTopicCommand = new MqttSubscribeTopicCommand();
        mqttSubscribeTopicCommand.setItems(items);
        List<MqttSubscribeResponse> resp = mqttServiceHandler.subscribe(mqttSubscribeTopicCommand);
        return new MqttSubAckResponse(packetIdentifier, resp).toBytes();
    }

    @Override
    protected void loadVariableHeader(ByteBuf buf) {
        byte first = buf.readByte();
        byte second = buf.readByte();
        this.packetIdentifier = first << 8 | second;
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
