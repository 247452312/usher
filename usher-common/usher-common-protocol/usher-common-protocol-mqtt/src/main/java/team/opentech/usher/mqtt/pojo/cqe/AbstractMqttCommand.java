package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;
import team.opentech.usher.util.Asserts;

/**
 * mqtt请求模板
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月06日 11时16分
 */
public abstract class AbstractMqttCommand extends AbstractCommand {

    protected final byte[] bytes;

    /**
     * 长度
     */
    protected Long length;

    protected AbstractMqttCommand(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * 解析协议
     */
    public void load() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(bytes);
        loadFixed(buf);
        loadVariableHeader(buf);
        loadPlayLoad(buf);
    }

    /**
     * 执行
     */
    public abstract byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler);

    /**
     * 加载可变头
     *
     * @param buf
     */
    protected abstract void loadVariableHeader(ByteBuf buf);

    /**
     * 加载playLoad
     *
     * @param buf
     */
    protected abstract void loadPlayLoad(ByteBuf buf);

    /**
     * 加载下一项字符串
     *
     * @param buf
     *
     * @return
     */
    protected String loadLastStringItem(ByteBuf buf) {
        int itemLength = loadLastInt(buf);
        byte[] clientIdentifier = new byte[itemLength];
        buf.readBytes(clientIdentifier);
        return new String(clientIdentifier, StandardCharsets.UTF_8);
    }

    /**
     * 获取下一项内容的长度
     *
     * @param buf
     *
     * @return
     */
    protected int loadLastInt(ByteBuf buf) {
        return (buf.readByte() << 8) + buf.readByte();
    }

    /**
     * 加载fix头
     *
     * @param buf
     */
    protected void loadFixed(ByteBuf buf) {
        // 跳过首位
        buf.setIndex(1, bytes.length);
        // 获取长度
        this.length = loadMqttLength(buf);
    }

    /**
     * 长度
     *
     * @return
     */
    protected long loadMqttLength(ByteBuf in) {
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
