package team.opentech.usher.mqtt.util;

import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import team.opentech.usher.annotation.Nullable;
import team.opentech.usher.mqtt.exception.MqttLengthException;
import team.opentech.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月05日 08时50分
 */
public class MqttUtil {

    @Nullable
    public static byte[] readPacket(ByteBuf in) {
        int length = getMqttLength(in, in.readerIndex());
        in.resetReaderIndex();
        // 半包问题
        if (in.readableBytes() < length) {
            return null;
        }
        byte[] dst = new byte[length];
        in.readBytes(dst, 0, length);
        return dst;
    }

    /**
     * 长度转换为 byte
     *
     * @param length
     *
     * @return
     */
    public static byte[] lengthToByte(long length) {
        if (length > 128 * 128 * 128) {
            throw new MqttLengthException(length);
        }
        long tempLength = length;
        byte target = (byte) (1 << 7);
        byte[] result = new byte[3];
        int count = 0;
        while (tempLength > target) {
            byte b = (byte) (tempLength & ~target | target);
            result[count] = b;
            tempLength >>= 8;
            count++;
        }
        if (count == 3) {
            return result;
        }
        byte[] dest = new byte[count];
        System.arraycopy(result, 0, dest, 0, count);
        return dest;
    }

    /**
     * 字符串转带有长度的协议体
     *
     * @param str
     *
     * @return
     */
    public static byte[] toLengthAndStr(String str) {
        byte[] bodyBytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] lengthBytes = lengthToByte(bodyBytes.length);
        return mergeBytes(lengthBytes, bodyBytes);
    }

    public static byte[] toIntBytes(Integer i) {
        byte firstByte = (byte) (i >> 8);
        byte secondByte = (byte) (i & ((1 << 8) - 1));
        return new byte[]{firstByte, secondByte};
    }

    /**
     * 合并多组bytes
     *
     * @return
     */
    public static byte[] mergeBytes(byte[]... bytes) {
        int index = 0;
        int sum = Arrays.stream(bytes).mapToInt(t -> t.length).sum();
        byte[] result = new byte[sum];
        for (byte[] aByte : bytes) {
            System.arraycopy(aByte, 0, result, index, aByte.length);
            index += aByte.length;
        }
        return result;
    }

    private static int getMqttLength(ByteBuf in, Integer startReadIndex) {
        in.setIndex(startReadIndex + 1, in.writerIndex());
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