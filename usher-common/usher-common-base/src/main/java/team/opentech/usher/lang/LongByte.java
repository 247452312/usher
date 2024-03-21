package team.opentech.usher.lang;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 可以自定义长度的byte数组,有基本的位运算方法
 * <p>
 * 由于位运算结果自动位int类型,所以此处使用int[]
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月21日 09时26分
 */
public class LongByte implements Serializable, Comparable<LongByte> {

    /**
     * int所占字节
     * 注: 最高位符号位不参与
     */
    private static final Integer INT_BIT_SIZE = 31;

    private int[] bytes;

    private int length;

    public LongByte(int[] bytes) {
        this.bytes = bytes;
        this.length = bytes.length * INT_BIT_SIZE;
    }

    public LongByte(int[] bytes, int length) {
        this.bytes = bytes;
        this.length = length;
    }

    public LongByte(String binaryString) {
        this.bytes = cutBinaryString(binaryString);
        this.length = binaryString.length();
    }

    public LongByte(LongByte bytes) {
        this.bytes = Arrays.copyOfRange(bytes.bytes, 0, bytes.byteSize());
        this.length = bytes.length;
    }

    public LongByte(int length) {
        int size = length / INT_BIT_SIZE;
        if (length % INT_BIT_SIZE != 0) {
            size++;
        }
        this.bytes = new int[size];
        this.length = length;
    }

    public int byteSize() {
        return bytes.length;
    }

    public int size() {
        return length;
    }

    public void setByte(int index, byte byt) {
        bytes[index] = byt;
    }

    public void set(int index, LongByte byt) {
        if (byt.length == 0) {
            return;
        }

        int sourceIndex = index;
        int paramIndex = 0;
        for (int i = 0; i < byt.length; i++) {
            int sourceByteIndex = sourceIndex / INT_BIT_SIZE;

            if (sourceByteIndex >= bytes.length) {
                int iss = 1;
            }
            int sourceByte = bytes[sourceByteIndex];
            int sourceByteShift = sourceIndex % INT_BIT_SIZE - 1;

            int paramByte = byt.bytes[paramIndex / INT_BIT_SIZE];
            int paramByteShift = paramIndex % INT_BIT_SIZE;

            int clearByte = sourceByte & ~(sourceByte & 1 << (INT_BIT_SIZE - sourceByteShift - 1));
            int bit = (paramByte >> (INT_BIT_SIZE - 1 - paramByteShift) & 1) << (INT_BIT_SIZE - sourceByteShift - 1);
            bytes[sourceByteIndex] = clearByte | bit;
            sourceIndex++;
            paramIndex++;
        }
    }

    /**
     * 部分取反
     *
     * @param index
     * @param size
     */
    public void negation(int index, int size) {
        for (int i = index; i < index + size; i++) {
            int byteIndex = i / INT_BIT_SIZE;
            int aByte = bytes[byteIndex];
            int shift = i % INT_BIT_SIZE;
            bytes[byteIndex] = aByte ^ 1 << (INT_BIT_SIZE - shift);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String binaryString = Integer.toBinaryString(bytes[i]);
            // 前置补0
            for (int j = 0; j < INT_BIT_SIZE - binaryString.length(); j++) {
                sb.append(0);
            }
            sb.append(binaryString);
        }
        if (bytes.length * INT_BIT_SIZE - length != 0) {
            sb.delete(sb.length() - (bytes.length * INT_BIT_SIZE - length), sb.length());
        }
        return sb.toString();
    }

    /**
     * 获取一个数组片段
     *
     * @param index 开始位置
     * @param size  长度
     *
     * @return
     */
    public LongByte get(int index, int size) {

        // 同一个byte里下标
        int tempByteIndex = 0;
        // result中byte的下标
        int resultByteIndex = 0;
        int length = size / INT_BIT_SIZE;
        if (size % INT_BIT_SIZE != 0) {
            length++;
        }
        int[] result = new int[length];
        for (int i = index; i < index + size; i++) {
            int byteIndex = i / INT_BIT_SIZE;
            int aByte = bytes[byteIndex];
            int shift = i % INT_BIT_SIZE;
            int b = aByte >> (INT_BIT_SIZE - shift - 1) & 1;
            result[resultByteIndex] |= b << (INT_BIT_SIZE - 1 - tempByteIndex++);
            if (tempByteIndex == INT_BIT_SIZE) {
                tempByteIndex = 0;
                resultByteIndex++;
            }
        }
        return new LongByte(result, size);
    }

    @Override
    public int compareTo(LongByte o) {
        int compare = Integer.compare(length, o.length);
        if (compare != 0) {
            return compare;
        }
        for (int i = 0; i < bytes.length; i++) {
            int compare1 = Integer.compare(bytes[i], o.bytes[i]);
            if (compare1 != 0) {
                return compare1;
            }
        }

        return 0;
    }

    /**
     * int值,如果超过int上限,则只返回前31bit作为int内容
     */
    public int intValue() {
        if (length >= INT_BIT_SIZE) {
            return bytes[0];
        }
        return bytes[0] >> (INT_BIT_SIZE - length);
    }

    /**
     * 将传入的二进制数组导入
     *
     * @return
     */
    private int[] cutBinaryString(String binaryString) {
        int size = binaryString.length() / INT_BIT_SIZE;
        if (binaryString.length() % INT_BIT_SIZE != 0) {
            size++;
        }
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            String sub;
            if (binaryString.length() < (i + 1) * INT_BIT_SIZE) {
                sub = binaryString.substring(i * INT_BIT_SIZE, binaryString.length());
                // 补0
                if (sub.length() < INT_BIT_SIZE) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < INT_BIT_SIZE - sub.length(); j++) {
                        sb.append(0);
                    }
                    sub = sub + sb;
                }
            } else {
                sub = binaryString.substring(i * INT_BIT_SIZE, (i + 1) * INT_BIT_SIZE);
            }
            result[i] = Integer.parseInt(sub, 2);
        }
        return result;
    }
}
