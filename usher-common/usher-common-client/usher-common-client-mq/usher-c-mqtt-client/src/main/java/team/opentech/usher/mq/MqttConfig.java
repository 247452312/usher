package team.opentech.usher.mq;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import team.opentech.usher.mq.client.MQClient;
import team.opentech.usher.mq.client.MQTTClient;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 17时04分
 */
public class MqttConfig {

    @Value("${mq.mqtt.serverURL:tcp://prod:1883}")
    private String serverURL;

    @Value("${mq.mqtt.username:admin}")
    private String username;

    @Value("${mq.mqtt.password:admin}")
    private String password;

    @Value("${mq.mqtt.clientName}")
    private String clientName;

    @Bean
    public MQClient consumerClient() throws MqttException {

        // host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
        MqttClient client = new MqttClient(serverURL, clientName, new MemoryPersistence());
        // MQTT的连接设置
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置连接的用户名
        options.setUserName(username);
        // 设置连接的密码
        options.setPassword(password.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);

        client.connect(options);

        return new MQTTClient(client);
    }
}
