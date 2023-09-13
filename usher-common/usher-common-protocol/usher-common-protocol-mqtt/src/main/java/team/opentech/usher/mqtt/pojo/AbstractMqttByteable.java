package team.opentech.usher.mqtt.pojo;

import team.opentech.usher.mqtt.enums.MqttCommandTypeEnum;
import team.opentech.usher.mqtt.util.MqttUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 11时13分
 */
public abstract class AbstractMqttByteable implements MqttByteable {

    @Override
    public byte[] toBytes() {
        /*MQTT 协议结构*/
        /*0.fix headers 不变头*/
        /*1.variable headers 可变头*/
        /*2.playLoad 协议体*/
        byte[] variableHeader = toVariableHeader();
        if (variableHeader == null) {
            variableHeader = new byte[0];
        }
        byte[] playLoad = toPlayLoad();
        if (playLoad == null) {
            playLoad = new byte[0];
        }
        byte[] fixedHeader = toFixedHeader(variableHeader.length + playLoad.length);
        byte[] bytes = new byte[fixedHeader.length + variableHeader.length + playLoad.length];

        System.arraycopy(fixedHeader, 0, bytes, 0, fixedHeader.length);
        System.arraycopy(variableHeader, 0, bytes, fixedHeader.length, variableHeader.length);
        if (playLoad.length > 0) {
            System.arraycopy(playLoad, 0, bytes, fixedHeader.length + variableHeader.length, playLoad.length);
        }
        return bytes;
    }

    /**
     * 响应体
     *
     * @return
     */
    protected abstract byte[] toPlayLoad();

    /**
     * 可变头
     *
     * @return
     */
    protected abstract byte[] toVariableHeader();

    /**
     * 固定头
     *
     * @return
     */
    protected byte[] toFixedHeader(long length) {
        MqttCommandTypeEnum mqttCommandTypeEnum = getType();
        Integer code = mqttCommandTypeEnum.getCode();
        byte firstByte = (byte) (code << 4 & firstFlags());
        byte[] bytes = MqttUtil.lengthToByte(length);
        byte[] result = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, result, 1, bytes.length);
        result[0] = firstByte;
        return result;
    }

    /**
     * 首位bytes的前四位
     */
    protected byte firstFlags() {
        return 0;
    }

    /**
     * 获取类型
     *
     * @return
     */
    protected abstract MqttCommandTypeEnum getType();
}
