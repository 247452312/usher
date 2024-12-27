package top.uhyils.usher.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.ustream.UStream;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月18日 16时58分
 */
class AccessTokenUtilTest {

    @Test
    void makeAccessToken() {
        UStream<String> stream = t -> {
            while (true) {
                t.accept(AccessTokenUtil.makeAccessToken());
            }
        };
        List<String> list = stream.takeWhile((index, String) -> index < 10000).toList();
        Set<String> set = new HashSet<>(list);
        Asserts.assertTrue(list.size() == set.size());
    }

    @Test
    void eraseAccessToken() {
        for (int i = 0; i < 4; i++) {
            System.out.println(AccessTokenUtil.eraseAccessToken(AccessTokenUtil.makeAccessToken()));
        }
    }
}
