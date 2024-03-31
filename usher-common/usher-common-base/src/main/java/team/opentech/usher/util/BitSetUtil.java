package team.opentech.usher.util;


import java.util.BitSet;
import org.mapstruct.ap.shaded.freemarker.template.utility.NumberUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月21日 17时38分
 */
public final class BitSetUtil {

    private BitSetUtil() {
        throw new RuntimeException("util不能被实例化");
    }

    /**
     * 交换
     *
     * @param first       第一个byte数组
     * @param firstIndex  开始位置
     * @param second      第二个byte数组
     * @param secondIndex 开始位置
     * @param size        长度
     */
    public static void swap(BitSet first, int firstIndex, BitSet second, int secondIndex, int size) {
        for (int i = 0; i < size; i++) {
            boolean b = first.get(firstIndex + i);
            first.set(firstIndex + i, second.get(secondIndex + i));
            second.set(secondIndex + i, b);
        }
    }

    /**
     * 设置
     *
     * @param first      第一个byte数组
     * @param firstIndex 开始位置
     * @param second     第二个byte数组
     */
    public static void set(BitSet first, int firstIndex, BitSet second) {
        for (int i = 0; i < second.length(); i++) {
            first.set(firstIndex + i, second.get(i));
        }
    }

    /**
     * 反转
     *
     * @param changeDna 修改的dna
     * @param index     开始位置
     * @param size      长度
     */
    public static void negation(BitSet changeDna, int index, int size) {
        for (int i = 0; i < size; i++) {
            int bitIndex = index + i;
            changeDna.set(bitIndex, !changeDna.get(bitIndex));
        }
    }

    /**
     * 字符串转bitSet
     *
     * @param binaryString
     *
     * @return
     */
    public static BitSet valueOf(String binaryString) {
        BitSet bitSet = new BitSet();
        for (int i = 0; i < binaryString.length(); i++) {
            char bitChar = binaryString.charAt(i);
            if (bitChar == '1') {
                bitSet.set(binaryString.length() - i - 1); // 设置第i位为true
            } else if (bitChar != '0') {
                throw new IllegalArgumentException("String contains non-binary character: " + bitChar);
            }
        }
        return bitSet;
    }

    /**
     * int转bitSet
     *
     * @param i
     *
     * @return
     */
    public static BitSet valueOf(int i) {
        return BitSet.valueOf(new long[]{i});
    }

    /**
     * 比较大小
     * <p>
     * 返回值参考{@link Integer#compareTo}
     *
     * @param first  第一个
     * @param second 第二个
     */
    public static int compareTo(BitSet first, BitSet second) {
        int result = Integer.compare(first.length(), second.length());
        if (result != 0) {
            return result;
        }
        long[] firstArray = first.toLongArray();
        long[] secondArray = second.toLongArray();
        result = Integer.compare(firstArray.length, secondArray.length);
        if (result != 0) {
            return result;
        }
        int index = firstArray.length - 1;
        while (index != -1) {
            result = Long.compare(firstArray[index], secondArray[index]);
            if (result != 0) {
                return result;
            }
            index--;
        }

        return 0;
    }

    /**
     * bitSet转为int
     * 如何位数超过int则丢失
     */
    public static int toInt(BitSet bitSet) {
        long[] longArray = bitSet.toLongArray();
        if (longArray.length == 0) {
            return 0;
        }
        return (int) longArray[0];
    }

    /**
     * bitSet转为long
     */
    public static long toLong(BitSet bitSet) {
        long[] longArray = bitSet.toLongArray();
        if (longArray.length == 0) {
            return 0;
        }
        return bitSet.toLongArray()[0];
    }
}
