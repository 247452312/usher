package team.opentech.usher.util;

import java.util.BitSet;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年04月01日 14时49分
 */
class BitSetUtilTest {

    @Test
    void getIntBySize() {
        BitSet bitSet = BitSetUtil.valueOf("10010100100101001110010111");
        int intBySize = BitSetUtil.getIntBySize(bitSet, 0, 3);
        Asserts.assertTrue(intBySize == 7);
        intBySize = BitSetUtil.getIntBySize(bitSet, 0, 4);
        Asserts.assertTrue(intBySize == 7);

        intBySize = BitSetUtil.getIntBySize(bitSet, 0, 5);
        Asserts.assertTrue(intBySize == 16 + 7);

    }


    @Test
    void getLongBySize() {
        BitSet bitSet = BitSetUtil.valueOf("10010100100101001110010111");
        long intBySize = BitSetUtil.getLongBySize(bitSet, 0, 3);
        Asserts.assertTrue(intBySize == 7);
        intBySize = BitSetUtil.getLongBySize(bitSet, 0, 4);
        Asserts.assertTrue(intBySize == 7);

        intBySize = BitSetUtil.getLongBySize(bitSet, 0, 5);
        Asserts.assertTrue(intBySize == 16 + 7);
    }


    @Test
    void getDoubleBySize() {
        long l = Double.doubleToLongBits(4.56);
        BitSet bitSet = BitSetUtil.valueOf(l);
        double aDouble = BitSetUtil.getDouble(bitSet, 0, 4);
        Asserts.assertTrue(aDouble == 4.56);
    }

    @Test
    void testDoubleLength() {
        int size = 1000;
        long[] param = new long[size];
        for (int i = 0; i < size; i++) {
            param[i] = RandomUtils.nextLong();
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            for (long l : param) {
                double temp = l;
                while (((int) temp) != 0) {
                    temp = temp / 10;
                }
            }
        }
        System.out.println("类型强制转换时间:" + (System.currentTimeMillis() - startTime) + " ms");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            for (long l : param) {
                double temp = l;
                while (Math.floor(temp) != 0) {
                    temp = temp / 10;
                }
            }
        }
        System.out.println("Math.floor时间:" + (System.currentTimeMillis() - startTime) + " ms");


    }

}
