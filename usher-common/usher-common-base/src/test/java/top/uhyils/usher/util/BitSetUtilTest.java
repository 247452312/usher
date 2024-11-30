package top.uhyils.usher.util;

import java.util.BitSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
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
        Asserts.assertTrue(intBySize == 14);

        intBySize = BitSetUtil.getIntBySize(bitSet, 0, 5);
        Asserts.assertTrue(intBySize == 29);
    }

    @Test
    void getBigBitSet() {
        BitSet bitSet = BitSetUtil.valueOf("100111001011110010100100101001110010111100101001001010011100101111001010010010100111001011110010100100101001110010111");
        int intBySize = BitSetUtil.getIntBySize(bitSet, 0, 6);
        Asserts.assertTrue(Objects.equals(intBySize, 58));
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
    void setIntBySize() {
        BitSet changeBit = BitSetUtil.valueOf("10010100100101001110010101");
        long longArray = changeBit.toLongArray()[0];
        String beforeChangeStr = Long.toBinaryString(longArray);

        AtomicInteger changeIndex = new AtomicInteger(1);
        int startIndex = changeIndex.get();
        float coeff = findFloat(changeBit, changeIndex);
        changeFloat(changeBit, startIndex, coeff);
        System.out.println("float: " + coeff);

        longArray = changeBit.toLongArray()[0];
        String afterChangeStr = Long.toBinaryString(longArray);

        Asserts.assertTrue(Objects.equals(beforeChangeStr, afterChangeStr));
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
        if (targetChangeNum >= 0) {
            // 1.修改符号位
            changeBit.set(startIndex++, true);
        } else {
            changeBit.set(startIndex++, false);
            targetChangeNum = -targetChangeNum;
        }

        // 2.修改整数位值
        BitSetUtil.setIntBySize(changeBit, startIndex, 5, (int) targetChangeNum);
        startIndex += 5;
        // 3.修改小数位值
        BitSetUtil.setIntBySize(changeBit, startIndex, 5, (int) ((targetChangeNum - (int) targetChangeNum) * 100));
    }

}
