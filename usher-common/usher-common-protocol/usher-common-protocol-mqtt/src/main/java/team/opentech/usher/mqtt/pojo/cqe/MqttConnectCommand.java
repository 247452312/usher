package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import team.opentech.usher.mqtt.enums.SessionPresentEnum;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.handler.cqe.MqttLoginCommand;
import team.opentech.usher.mqtt.handler.resp.MqttLoginResponse;
import team.opentech.usher.mqtt.pojo.resp.MqttConneckResponse;
import team.opentech.usher.mqtt.pojo.resp.MqttResponse;
import team.opentech.usher.util.Asserts;

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
    public void load() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(bytes);
        // 跳过首位
        buf.setIndex(1, bytes.length);

        // 获取长度
        this.length = loadMqttLength(buf);

        // 跳过协议名称
        passProtocolName(buf);

        //协议版本
        this.version = loadVersion(buf);

        // 连接标识  1->UserNameFlag 2->PasswordFlag 3->WillRetain 4,5->WillQos 6->willFlag 7->CleanSession 8->Reserved
        this.connectFlags = loadConnectFlags(buf);

        // 心跳间隔
        this.keepAlive = loadKeepAlive(buf);

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

    @Override
    public byte[] invoke(MqttServiceHandler mqttServiceHandler) {
        MqttLoginResponse login = mqttServiceHandler.login(MqttLoginCommand.build(username, password));
        if (login.getSuccess()) {
            // todo 成功了 加session
        }
        MqttResponse mqttResponse = new MqttConneckResponse(SessionPresentEnum.ACCEPTED);
        return mqttResponse.toBytes();
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

    /**
     * 加载下一项字符串
     *
     * @param buf
     *
     * @return
     */
    private String loadLastStringItem(ByteBuf buf) {
        int itemLength = loadLastItemLength(buf);
        byte[] clientIdentifier = new byte[itemLength];
        buf.readBytes(clientIdentifier);
        return new String(clientIdentifier, StandardCharsets.UTF_8);
    }

    private int loadKeepAlive(ByteBuf buf) {
        return loadLastItemLength(buf);
    }

    /**
     * 获取下一项内容的长度
     *
     * @param buf
     *
     * @return
     */
    private int loadLastItemLength(ByteBuf buf) {
        return (buf.readByte() << 8) + buf.readByte();
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

    /**
     * 长度
     *
     * @return
     */
    private long loadMqttLength(ByteBuf in) {
        // 前三位长度判断
        int length = 0;
        for (int i = 0; i < 3; i++) {
            // 第一位
            byte lb = in.readByte();
            if ((lb & 0x10000000) >> 7 == 0) {
                length += lb << (8 * i);
                // 协议总长度 = 协议头(1+长度占位) + 可变 + 协议体
                return length + i + 2;
            }
            length = lb & 0x01111111;
        }

        byte lb = in.readByte();
        Asserts.assertTrue((lb & 0x10000000) >> 7 == 0, "长度错误");

        int result = length + (lb << 24);
        // 协议总长度 = 协议头(1+长度占位) + 可变 + 协议体
        result += 1 + 4;
        return result;
    }
}
