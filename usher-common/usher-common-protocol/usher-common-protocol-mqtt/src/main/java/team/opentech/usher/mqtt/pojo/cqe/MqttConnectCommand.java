package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import team.opentech.usher.mqtt.context.MqttContext;
import team.opentech.usher.mqtt.enums.SessionPresentEnum;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.handler.cqe.MqttLoginCommand;
import team.opentech.usher.mqtt.handler.resp.MqttLoginResponse;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.mqtt.pojo.resp.MqttConneckResponse;
import team.opentech.usher.mqtt.pojo.resp.MqttResponse;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月06日 11时17分
 */
public class MqttConnectCommand extends AbstractMqttCommand {

    /**
     * 版本
     */
    private Integer version;

    /**
     * 连接标识
     */
    private Integer connectFlags;

    /**
     * 心跳间隔
     */
    private Integer keepAlive;

    /**
     * 用户clientName
     */
    private String clientIdentifier;

    /**
     * 遗言topic
     */
    private String willTopic;

    /**
     * 遗言message
     */
    private String willMessage;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public MqttConnectCommand(byte[] bytes) {
        super(bytes);
    }

    @Override
    public byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler) {
        MqttLoginResponse login = mqttServiceHandler.login(MqttLoginCommand.build(username, password));
        if (login.getSuccess()) {
            MqttContext.add(clientIdentifier, nettyHandler);
            nettyHandler.loginSuccess(clientIdentifier);
        }
        MqttResponse mqttResponse = new MqttConneckResponse(SessionPresentEnum.ACCEPTED);
        return mqttResponse.toBytes();
    }

    @Override
    protected void loadVariableHeader(ByteBuf buf) {
        // 跳过协议名称
        passProtocolName(buf);

        //协议版本
        this.version = loadVersion(buf);

        // 连接标识  1->UserNameFlag 2->PasswordFlag 3->WillRetain 4,5->WillQos 6->willFlag 7->CleanSession 8->Reserved
        this.connectFlags = loadConnectFlags(buf);

        // 心跳间隔
        this.keepAlive = loadKeepAlive(buf);
    }

    @Override
    protected void loadPlayLoad(ByteBuf buf) {
        // clientIdentifier
        this.clientIdentifier = loadClientIdentifier(buf);

        // willTopic 遗言
        if (((connectFlags >> 2) & 1) == 1) {
            this.willTopic = loadWillTopic(buf);
            this.willMessage = loadWillMessage(buf);
        }

        // username
        if (((connectFlags >> 7) & 1) == 1) {
            this.username = loadUserName(buf);
        }
        // password
        if (((connectFlags >> 6) & 1) == 1) {
            this.password = loadPassword(buf);
        }
    }

    private String loadPassword(ByteBuf buf) {
        return loadLastStringItem(buf);
    }

    private String loadUserName(ByteBuf buf) {
        return loadLastStringItem(buf);
    }

    private String loadWillMessage(ByteBuf buf) {
        return loadLastStringItem(buf);
    }

    private String loadWillTopic(ByteBuf buf) {
        return loadLastStringItem(buf);
    }

    private String loadClientIdentifier(ByteBuf buf) {
        return loadLastStringItem(buf);
    }

    private int loadKeepAlive(ByteBuf buf) {
        return loadLastInt(buf);
    }


    private int loadConnectFlags(ByteBuf buf) {
        return buf.readByte();
    }

    private int loadVersion(ByteBuf buf) {
        return buf.readByte();
    }

    private void passProtocolName(ByteBuf buf) {
        buf.setIndex(buf.readerIndex() + 6, buf.writerIndex());
    }

}
