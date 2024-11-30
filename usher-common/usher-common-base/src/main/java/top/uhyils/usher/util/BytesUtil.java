package top.uhyils.usher.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月21日 09时54分
 */
public class BytesUtil {

    /**
     * int型占4位
     */
    private static final Integer INTEGER_BYTE_SIZE = 4;

    /**
     * long型占8位
     */
    private static final Integer LONG_BYTE_SIZE = 8;

    /**
     * 1byte占8bit
     */
    private static final Integer BYTE_TO_BIT_SIZE = 8;

    /**
     * 4k 缓冲区
     */
    private static final int BUFFER_SIZE = 4096;

    private BytesUtil() {
    }

    public static void swapSlice(byte[] firstBytes, int firstIndex, byte[] secondBytes, int secondIndex, int size) {
        for (int i = 0; i < size; i++) {
            byte aByte = firstBytes[firstIndex];
            firstBytes[firstIndex] = secondBytes[secondIndex];
            secondBytes[secondIndex] = aByte;
            firstIndex++;
            secondIndex++;
        }
    }

    public static byte[] compress(byte[] bytes) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(bytes);
            gzip.flush();
            gzip.finish();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("gzip compress error", e);
        }
    }

    public static byte[] uncompress(byte[] bytes) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); GZIPInputStream gunzip = new GZIPInputStream(new ByteArrayInputStream(bytes))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int n;
            while ((n = gunzip.read(buffer)) > -1) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("gzip decompress error", e);
        }
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * byte数组变Integer
     *
     * @param data
     *
     * @return
     */
    public static Integer changeByteToInteger(byte[] data) {
        if (data.length > INTEGER_BYTE_SIZE) {
            throw new ArrayIndexOutOfBoundsException(String.format("int需要%d位数组,您传入了%d位数组", INTEGER_BYTE_SIZE, data.length));
        }
        return changeByteToInteger(data, 0, data.length);
    }

    /**
     * byte数组变Integer
     *
     * @param data
     *
     * @return
     */
    public static Integer changeByteToInteger(byte[] data, int startIndex, int size) {
        Asserts.assertTrue(data.length >= (startIndex + size), "数组转换int时超出界限");
        int result = 0;
        int shiftLeftSize = 0;
        for (int i = startIndex + size - 1; i >= startIndex; i--) {
            result += (0xff & data[i]) << shiftLeftSize;
            shiftLeftSize += BYTE_TO_BIT_SIZE;
        }

        return result;
    }

    /**
     * Integer变byte数组
     * <p>
     * 大端模式
     */
    public static byte[] changeIntegerToByte(Integer data) {
        byte[] result = new byte[INTEGER_BYTE_SIZE];
        int shift = BYTE_TO_BIT_SIZE * INTEGER_BYTE_SIZE;
        for (int i = 0; i < INTEGER_BYTE_SIZE; i++) {
            shift -= BYTE_TO_BIT_SIZE;
            result[i] = (byte) ((data >> shift) & 0b11111111);
        }
        return result;
    }

    /**
     * Long变byte数组
     *
     * @param data
     *
     * @return
     */
    public static byte[] changeLongToByte(Long data) {
        byte[] result = new byte[LONG_BYTE_SIZE];
        int shift = BYTE_TO_BIT_SIZE * LONG_BYTE_SIZE;
        for (int i = 0; i < LONG_BYTE_SIZE; i++) {
            shift -= BYTE_TO_BIT_SIZE;
            result[i] = (byte) ((data >>> shift) & 0b11111111);
        }
        return result;
    }

    /**
     * byte数组变Long
     *
     * @param data
     *
     * @return
     */
    public static Long changeByteToLong(byte[] data) {
        if (data.length > LONG_BYTE_SIZE) {
            throw new ArrayIndexOutOfBoundsException(String.format("long需要%d位数组,您传入了%d位数组", LONG_BYTE_SIZE, data.length));
        }
        long result = 0;
        for (int i = 0; i < LONG_BYTE_SIZE; ++i) {
            result <<= BYTE_TO_BIT_SIZE;
            result |= (data[i] & 0xff);
        }
        return result;
    }

    /**
     * 取反
     *
     * @param bytes 要取反的数组
     * @param index 开始的下标
     * @param size  取反的长度
     */
    public static void negation(byte[] bytes, int index, int size) {
        for (int i = 0; i < size; i++) {
            bytes[index] = (byte) ~bytes[index];
        }
    }

}
