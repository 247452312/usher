package team.opentech.usher.common.util;

import io.netty.buffer.ByteBuf;
import team.opentech.usher.util.Pair;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月19日 09时11分
 */
public final class DecentralizedProtocolUtil {


    private DecentralizedProtocolUtil() {
        throw new RuntimeException("工具类不能实例化");
    }


    public static byte[] lengthToBytes(int length) {
        if (length <= 250) {
            return new byte[]{(byte) length};
        }
        if (length <= 2 << 16) {
            byte[] bytes = new byte[3];
            bytes[0] = (byte) 0b11111100;
            fillByteLast(bytes, length);
            return bytes;
        }
    }

    /**
     * 去中心化集群长度获取
     *
     * @param in
     *
     * @return
     */
    public static int bytesToLength(ByteBuf in) {
        byte firstByte = in.readByte();

        if (firstByte >= 0 && firstByte <= 0b11111011) {
            return firstByte;
        }
        if (firstByte == 0b11111100) {
            return bytesToLength(2, in);
        }
        if (firstByte == 0b11111101) {
            return bytesToLength(3, in);
        }
        if (firstByte == 0b11111110) {
            return bytesToLength(8, in);
        }
        return 0;
    }

    public static int bytesToLength(byte[] headerAndBody) {
        byte firstByte = headerAndBody[0];
        if (firstByte >= 0 && firstByte <= 0b11111011) {
            return firstByte;
        }
        if (firstByte == 0b11111100) {
            return bytesToLength(2, headerAndBody);
        }
        if (firstByte == 0b11111101) {
            return bytesToLength(3, headerAndBody);
        }
        if (firstByte == 0b11111110) {
            return bytesToLength(8, headerAndBody);
        }

        return 0;
    }

    /**
     * 协议中切割header和body
     *
     * @param headerAndBody
     *
     * @return
     */
    public static Pair<byte[], byte[]> subHeaderAndBody(byte[] headerAndBody) {
        byte firstByte = headerAndBody[0];
        int offSet = 0;
        int length = 0;
        if (firstByte >= 0 && firstByte <= 0b11111011) {
            length = firstByte;
        } else if (firstByte == 0b11111100) {
            offSet = 2;
            length = bytesToLength(offSet, headerAndBody);
        } else if (firstByte == 0b11111101) {
            offSet = 3;
            length = bytesToLength(offSet, headerAndBody);
        } else if (firstByte == 0b11111110) {
            offSet = 8;
            length = bytesToLength(8, headerAndBody);
        }

        byte[] header = new byte[length];
        System.arraycopy(headerAndBody, offSet + 1, header, 0, length);
        int bodyLength = headerAndBody.length - (offSet + 1 + length);
        byte[] body = new byte[bodyLength];
        System.arraycopy(headerAndBody, offSet + 1 + length, body, 0, bodyLength);
        return new Pair<>(header, body);
    }

    private static int bytesToLength(int length, byte[] bytes) {
        int value = 0;
        for (int i = length - 1; i > 0; i--) {
            value |= bytes[i] & 0xFF;
            value <<= 8;
        }
        value |= bytes[0] & 0xFF;
        return value;
    }

    private static void fillByteLast(byte[] bytes, int length) {
        int temp = length;
        int index = 1;
        while (temp > 0) {
            bytes[index++] = (byte) (temp & 0xFF);
            temp >>= 8;
        }
    }

    private static int bytesToLength(int x, ByteBuf in) {
        int value = 0;
        byte[] dst = new byte[x];
        in.readBytes(dst);
        for (int i = dst.length - 1; i > 0; i--) {
            value |= dst[i] & 0xFF;
            value <<= 8;
        }
        value |= dst[0] & 0xFF;
        return value;
    }


}
