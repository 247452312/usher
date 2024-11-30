package top.uhyils.usher.pojo.entity.type;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月17日 09时07分
 */
class PasswordTest {

    @Test
    void encode() {
        Password password = new Password("123456");
        String encode = password.encode();
        Asserts.assertTrue(Objects.equals(encode, "/I8yU670IvjIawRGUBnTgA=="));
    }

    @Test
    void decode() {
        Password password = new Password("lMNDs2a/MP9N0FStLWVEDQ==");
        String decode = password.decode();
        Asserts.assertTrue(Objects.equals(decode, "123456"));
    }
}
