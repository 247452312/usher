package team.opentech.usher.mqtt.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mqtt.decode.MqttDecoder;
import team.opentech.usher.mqtt.enums.MqttCommandTypeEnum;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.pojo.cqe.AbstractMqttCommand;
import team.opentech.usher.mqtt.pojo.cqe.MqttConnectCommand;
import team.opentech.usher.util.Asserts;

/**
 * mqtt协议处理器
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月04日 09时19分
 */
public class MqttNettyHandler extends ChannelInboundHandlerAdapter implements ChannelInboundHandler {


    private final MqttServiceHandler handler;

    /**
     * 连接
     */
    private Channel mqttChannel;


    public MqttNettyHandler(MqttServiceHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }

    /**
     * 初始化连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }

    /**
     * 接收到信息时调用
     *
     * @param ctx
     * @param msg
     *
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        this.mqttChannel = ctx.channel();
        /**
         * 因{@link MqttDecoder} 所以这里一定为byte[]
         */
        byte[] mqttBytes = (byte[]) msg;
        AbstractMqttCommand mqttCommand = loadToCommand(mqttBytes);
        mqttCommand.load();
        byte[] invoke = mqttCommand.invoke(handler);
        send(invoke);
    }

    /**
     * 关闭
     */
    public void closeOnFlush() {
        if (mqttChannel != null && mqttChannel.isActive()) {
            mqttChannel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 加载请求类型
     *
     * @param mqttBytes
     *
     * @return
     */
    @NotNull
    private AbstractMqttCommand loadToCommand(byte[] mqttBytes) {
        byte mqttByte = mqttBytes[0];
        MqttCommandTypeEnum mqttType = MqttCommandTypeEnum.getByCode((int) mqttByte);
        switch (mqttType) {
            case CONNECT:
                return new MqttConnectCommand(mqttBytes);
            case PUBACK:
            case PUBREC:
            case PUBREL:
            case SUBACK:
            case PINGREQ:
            case PUBCOMP:
            case PUBLISH:
            case PINGRESP:
            case UNSUBACK:
            case RESERVERD:
            case SUBSCRIBE:
            case DISCONNECT:
            case UNSUBACRIBE:
            case CONNACK:
            default:
                Asserts.throwException("未支持mqtt请求的指定类型");
                return null;
        }
    }

    /**
     * 发送数据
     *
     * @param msg
     */
    private void send(byte[] msg) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg);
        this.mqttChannel.writeAndFlush(buf);
    }

}
