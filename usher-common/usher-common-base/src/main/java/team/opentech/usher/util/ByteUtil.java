package team.opentech.usher.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.commons.io.HexDump;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月05日 08时46分
 */
public final class ByteUtil {

    private ByteUtil() {
        throw new RuntimeException("ByteUtil不能创建实例");
    }

    /**
     * 将协议解析为差不多看得懂的东西,但不能用 只能输出
     *
     * @param packet
     *
     * @return
     */
    public static String dump(byte[] packet) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            HexDump.dump(packet, 0, out, 0);
            return out.toString();
        } catch (IOException e) {
            LogUtil.error(e);
            return "";
        }
    }

    /**
     * 从开始对比两个byte是否匹配
     *
     * @param firstBytes  第一个
     * @param secondBytes 第二个
     * @param length      要对比的长度
     *
     * @return
     */
    public static boolean equalsFromStart(byte[] firstBytes, byte[] secondBytes, int length) {
        if (firstBytes.length < length || secondBytes.length < length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (firstBytes[i] != secondBytes[i]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] subByte(byte[] bytes, int i) {
        int length = bytes.length - 1;
        byte[] result = new byte[length];
        System.arraycopy(bytes, i, result, 0, length);
        return result;
    }

    public static byte[] subByte(byte[] bytes, int start, int end) {
        Asserts.assertTrue(end > start);
        int length = end - start;
        byte[] result = new byte[length];
        System.arraycopy(bytes, start, result, 0, length);
        return result;
    }
}
