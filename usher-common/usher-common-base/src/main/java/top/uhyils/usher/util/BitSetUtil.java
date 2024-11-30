package top.uhyils.usher.util;


import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

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
                // 设置第i位为true
                bitSet.set(binaryString.length() - i - 1);
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
     * long转bitSet
     *
     * @param i
     *
     * @return
     */
    public static BitSet valueOf(long i) {
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

    /**
     * 根据size获取
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     * @param size       长度
     *
     * @return
     */
    public static BitSet getBySize(BitSet bitSet, Integer startIndex, Integer size) {
        return bitSet.get(startIndex, startIndex + size);
    }

    /**
     * 根据指定size获取一个int
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     * @param size       长度
     *
     * @return
     */
    public static int getIntBySize(BitSet bitSet, Integer startIndex, Integer size) {
        BitSet tempBitSet = bitSet.get(startIndex, startIndex + size);
        long[] longArray = tempBitSet.toLongArray();
        if (longArray.length == 0) {
            return 0;
        }
        return (int) longArray[0];
    }

    /**
     * 根据指定size获取一个int
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     * @param size       长度
     *
     * @return
     */
    public static void setIntBySize(BitSet bitSet, Integer startIndex, Integer size, int value) {
        for (int i = 0; i < size; i++) {
            bitSet.set(startIndex + size - 1 - i, (value & 1) == 1);
            value >>= 1;
        }
    }

    /**
     * 根据一个int
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     *
     * @return
     */
    public static int getInt(BitSet bitSet, Integer startIndex) {
        return getIntBySize(bitSet, startIndex, Integer.SIZE);
    }


    /**
     * 根据指定size获取一个int
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     * @param size       长度
     *
     * @return
     */
    public static int getIntBySize(BitSet bitSet, AtomicInteger startIndex, Integer size) {
        int result = getIntBySize(bitSet, startIndex.get(), size);
        startIndex.addAndGet(size);
        return result;
    }

    /**
     * 根据一个int
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     *
     * @return
     */
    public static int getInt(BitSet bitSet, AtomicInteger startIndex) {
        return getIntBySize(bitSet, startIndex, Integer.SIZE);
    }


    /**
     * 根据指定size获取一个long
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     * @param size       长度
     *
     * @return
     */
    public static long getLongBySize(BitSet bitSet, Integer startIndex, Integer size) {
        long[] longArray = bitSet.get(startIndex, startIndex + size).toLongArray();
        if (longArray.length == 0) {
            return 0;
        }
        return longArray[0];
    }

    /**
     * 根据一个long
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     *
     * @return
     */
    public static long getLong(BitSet bitSet, Integer startIndex) {
        return getLongBySize(bitSet, startIndex, Long.SIZE);
    }

    /**
     * 根据指定size获取一个long
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     * @param size       长度
     *
     * @return
     */
    public static long getLongBySize(BitSet bitSet, AtomicInteger startIndex, Integer size) {
        long result = getLongBySize(bitSet, startIndex.get(), size);
        startIndex.addAndGet(size);
        return result;
    }

    /**
     * 根据一个long
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     *
     * @return
     */
    public static long getLong(BitSet bitSet, AtomicInteger startIndex) {
        return getLongBySize(bitSet, startIndex, Long.SIZE);
    }

    /**
     * 根据指定size获取一个double
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     * @param size       长度
     *
     * @return
     */
    public static double getDoubleBySize(BitSet bitSet, Integer startIndex, Integer size) {
        long longBySize = getLongBySize(bitSet, startIndex, size);
        return Double.longBitsToDouble(longBySize);
    }

    /**
     * 根据指定size获取一个double
     *
     * @param bitSet        原始bitSet
     * @param startIndex    开始位置
     * @param intSize       整数部分长度
     * @param decimalSize   小数部分在bit上的长度
     * @param decimalLength 小数部分保留长度
     *
     * @return
     */
    public static double getDoubleBySize(BitSet bitSet, int startIndex, int intSize, int decimalSize, int decimalLength) {
        int intNum = getIntBySize(bitSet, startIndex, intSize);

        int decimalNum = getIntBySize(bitSet, startIndex, decimalSize);
        int i = 1;
        for (int j = 0; j < decimalLength; j++) {
            i *= 10;
        }
        double v = decimalNum * 1.0 / i;
        return intNum + v - Math.floor(v);
    }

    /**
     * 根据指定size获取一个double
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     * @param size       整数部分长度
     *
     * @return
     */
    public static double getDoubleBySize(BitSet bitSet, int startIndex, int size) {
        long longBySize = getLongBySize(bitSet, startIndex, size);
        return Double.longBitsToDouble(longBySize);
    }

    /**
     * 获取一个double
     *
     * @param bitSet      原始bitSet
     * @param startIndex  开始位置
     * @param decimalSize 小数部分长度
     *
     * @return
     */
    public static double getDouble(BitSet bitSet, Integer startIndex, int decimalSize) {
        return getDoubleBySize(bitSet, startIndex, Double.SIZE / 2, Double.SIZE / 2, decimalSize);
    }

    /**
     * 根据指定size获取一个double
     *
     * @param bitSet      原始bitSet
     * @param startIndex  开始位置
     * @param size        长度
     * @param decimalSize 小数部分长度
     *
     * @return
     */
    public static double getDoubleBySize(BitSet bitSet, AtomicInteger startIndex, Integer size, int decimalSize) {
        double doubleBySize = getDoubleBySize(bitSet, startIndex.get(), size / 2, size / 2 + size % 2, decimalSize);
        startIndex.getAndAdd(size);
        return doubleBySize;
    }

    /**
     * 根据指定size获取一个double
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     * @param size       长度
     *
     * @return
     */
    public static double getDoubleBySize(BitSet bitSet, AtomicInteger startIndex, Integer size) {
        Integer i = startIndex.get();
        double doubleBySize = getDoubleBySize(bitSet, i, size);
        startIndex.getAndAdd(size);
        return doubleBySize;
    }

    /**
     * 获取一个double
     *
     * @param bitSet      原始bitSet
     * @param startIndex  开始位置
     * @param decimalSize 小数部分长度
     *
     * @return
     */
    public static double getDouble(BitSet bitSet, AtomicInteger startIndex, int decimalSize) {
        return getDoubleBySize(bitSet, startIndex, Double.SIZE, decimalSize);
    }

    /**
     * 获取一个double
     *
     * @param bitSet     原始bitSet
     * @param startIndex 开始位置
     *
     * @return
     */
    public static double getDouble(BitSet bitSet, AtomicInteger startIndex) {
        return getDoubleBySize(bitSet, startIndex, Double.SIZE);
    }
}
