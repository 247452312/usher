package team.opentech.usher.util;

import java.util.BitSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
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

    @Test
    void setIntBySize() {
        BitSet changeBit = BitSetUtil.valueOf("10010100100101001110010101");
        long[] longArray = changeBit.toLongArray();
        String string = changeBit.toString();
        AtomicInteger changeIndex = new AtomicInteger(1);
        int startIndex = changeIndex.get();
        float coeff = findFloat(changeBit, changeIndex);
        changeFloat(changeBit, startIndex, coeff);
        Asserts.assertTrue(Objects.equals(changeBit.toString(),string));
        int i = 1;

    }

    private float findFloat(BitSet changeBit, AtomicInteger changeIndex) {
        boolean b = changeBit.get(changeIndex.getAndIncrement());
        int intNum = BitSetUtil.getIntBySize(changeBit, changeIndex, 5);
        float v = BitSetUtil.getIntBySize(changeBit, changeIndex, 5) / 100f;
        float decimalNum = (float) (v - Math.floor(v));
        float v1 = intNum + decimalNum;
        if (!b) {
            v1 = -v1;
        }
        return v1;
    }


    /**
     * 将一个小数放置到bitSet中去
     *
     * @param changeBit       待修改的dna
     * @param startIndex      起始下标
     * @param targetChangeNum 要修改成的值
     */
    private void changeFloat(BitSet changeBit, int startIndex, double targetChangeNum) {
        // 1.修改符号位
        changeBit.set(startIndex++, targetChangeNum >= 0);
        // 2.修改整数位值
        BitSetUtil.setIntBySize(changeBit, startIndex, 5, (int) targetChangeNum);
        startIndex += 5;
        // 3.修改小数位值
        BitSetUtil.setIntBySize(changeBit, startIndex, 5, (int) ((targetChangeNum - (int) targetChangeNum) * 100));
    }

}
