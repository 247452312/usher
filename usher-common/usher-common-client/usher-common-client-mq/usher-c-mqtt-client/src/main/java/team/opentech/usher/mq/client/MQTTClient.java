package team.opentech.usher.mq.client;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.consumer.MQConsumer;
import team.opentech.usher.mq.pojo.BackInfo;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.CollectionUtil;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 17时35分
 */
public class MQTTClient extends AbstractMQClient {

    private final MqttClient client;

    public MQTTClient(MqttClient client) {
        this.client = client;
        client.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable cause) {
                LogUtil.error(cause, "MQTT连接异常");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                List<MQConsumer> mqConsumers = consumerListMap.get(topic);
                if (CollectionUtil.isEmpty(mqConsumers)) {
                    LogUtil.warn("未找到topic:{},对应的consumer", topic);
                }
                for (MQConsumer consumer : mqConsumers) {
                    consumer.receive(new MQMessage(topic, new String(message.getPayload(), StandardCharsets.UTF_8)));
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                try {
                    if (token == null) {
                        return;
                    }
                    MqttMessage message = token.getMessage();
                    if (message == null) {
                        return;
                    }
                    int id = message.getId();
                    BackInfo backInfo = new BackInfo();
                    backInfo.setSuccess(token.isComplete());
                    backInfo.setMsg(null);
                    dealCallBack((long) id, backInfo);
                } catch (MqttException e) {
                    LogUtil.error(this, e);
                }
            }
        });
    }

    @Override
    public Long sendOriginal(MQMessage message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.body().getBytes(StandardCharsets.UTF_8));
            Long id = message.id();
            mqttMessage.setId(id.intValue());
            client.publish(message.topicName(), mqttMessage);
            return (long) mqttMessage.getId();
        } catch (MqttException e) {
            Asserts.throwException(e);
            return null;
        }
    }


    @Override
    protected void doAddConsumer(MQConsumer consumer) {
        try {
            client.subscribe(consumer.topic());
        } catch (MqttException e) {
            Asserts.throwException(e);
        }
    }
}
