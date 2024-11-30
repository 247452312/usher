package top.uhyils.usher.lang;

import java.util.BitSet;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.BitSetUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月21日 10时45分
 */
class BitSetTest {


    @Test
    void set() {
        BitSet longByte = BitSetUtil.valueOf("11");
        BitSetUtil.set(longByte, 4, BitSetUtil.valueOf("1000"));
        BitSet bitSet = BitSetUtil.valueOf("10000011");
        for (int i = 0; i < bitSet.length(); i++) {
            Asserts.assertTrue(bitSet.get(i) == longByte.get(i));
        }
    }

    @Test
    void testStrInt() {
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
        int i1 = Integer.parseInt(Integer.toBinaryString(Integer.MAX_VALUE), 2);
        System.out.println(i1);
        int i = Integer.parseInt("1110010100000000000000000000000", 2);
        System.out.println(i);
    }

    @Test
    void testGet() {
        BitSet longByte = BitSetUtil.valueOf("11010000001");
        BitSet longByte1 = longByte.get(6, 10);
        BitSet temp = BitSetUtil.valueOf("1010");
        for (int i = 0; i < temp.length(); i++) {
            Asserts.assertTrue(temp.get(i) == longByte1.get(i));
        }
    }

    @Test
    void testCompare() {
        BitSet longByte1 = BitSetUtil.valueOf("11011000011110110000111101100001111011000011110110000111101100001111011000011110110000111101100001111011000011110110000111101100001111011000011");
        BitSet longByte2 = BitSetUtil.valueOf("11010101001110101010011101010100111010101001110101010011101010100111010101001110101010011101010100111010101001110101010011101010100111010101001");
        int i = BitSetUtil.compareTo(longByte1, longByte2);
        Asserts.assertTrue(i == 1);

        longByte1 = BitSetUtil.valueOf("1100");
        longByte2 = BitSetUtil.valueOf("1010");
        i = BitSetUtil.compareTo(longByte1, longByte2);
        Asserts.assertTrue(i == 1);

        longByte1 = BitSetUtil.valueOf("1010");
        longByte2 = BitSetUtil.valueOf("1100");
        i = BitSetUtil.compareTo(longByte1, longByte2);
        Asserts.assertTrue(i == -1);
    }

    @Test
    void testValueOf() {
        BitSet longByte = BitSetUtil.valueOf(0b11011000011);
        BitSet longByte1 = BitSetUtil.valueOf("11011000011");
        for (int i = 0; i < longByte.length(); i++) {
            Asserts.assertTrue(longByte.get(i) == longByte1.get(i));
        }
    }

    @Test
    void testToInt() {
        BitSet longByte = BitSetUtil.valueOf(45);
        int anInt = BitSetUtil.toInt(longByte);
        Asserts.assertTrue(anInt == 45);
    }

    @Test
    void testSize() {
        long[] longs = new long[140];
        for (int i = 0; i < 140; i++) {
            longs[i] = RandomUtils.nextLong();
        }
        BitSet longByte = BitSet.valueOf(longs);
        int size = longByte.size();
        Asserts.assertTrue(size == 140 * 64, "错误,size不正确");
        int length = longByte.length();
        int i1 = 140 * 64 - 1;
        Asserts.assertTrue(length == i1, "错误,length不正确" + length + " " + i1);
        int i = 1;
    }
}
