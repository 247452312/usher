package top.uhyils.usher.rpc.netty.util;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.springframework.util.Assert;
import top.uhyils.usher.rpc.util.RpcAssertUtil;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.BytesUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月22日 11时05分
 */
class BytesUtilsTest {

    @org.junit.jupiter.api.Test
    public void testChangeLongToByte() {
        Long unique = 1760402212572365792L;
        byte[] bytes = BytesUtil.changeLongToByte(unique);
        Long aLong = BytesUtil.changeByteToLong(bytes);
        Asserts.assertTrue(Objects.equals(unique, aLong));
    }

    @org.junit.jupiter.api.Test
    void changeIntegerToByte() {
        int data = 65535;
        byte[] bytes = BytesUtil.changeIntegerToByte(data);
        Integer integer = BytesUtil.changeByteToInteger(bytes);
        Assert.isTrue(true, "");
    }

    @org.junit.jupiter.api.Test
    void testCompress() {
        String temp = "uhyils_usherrpc";
        byte[] compress = BytesUtil.compress(temp.getBytes(StandardCharsets.UTF_8));
        byte[] uncompress = BytesUtil.uncompress(compress);
        String s = new String(uncompress, StandardCharsets.UTF_8);
        System.out.println(s);
        RpcAssertUtil.assertTrue(Objects.equals(s, temp));
    }
}
