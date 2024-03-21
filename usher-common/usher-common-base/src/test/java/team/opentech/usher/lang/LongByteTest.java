package team.opentech.usher.lang;

import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月21日 10时45分
 */
class LongByteTest {

    @Test
    void set() {
        LongByte longByte = new LongByte("11100101");
        longByte.set(4, new LongByte("1000"));
        System.out.println(longByte);
        //        11111101
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
    void testIntValue() {
        LongByte longByte = new LongByte("111"); // 7
        System.out.println(longByte.intValue());

        longByte = new LongByte("1110"); // 14
        System.out.println(longByte.intValue());


    }
}
