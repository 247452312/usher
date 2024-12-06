package top.uhyils.usher.pojo.entity.link.http;


import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.pojo.link.HttpLinkInfo;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 14时41分
 */
class HttpLinkTest {

    @Test
    void replace() {
        HttpLink link = new HttpLink("", new HttpLinkInfo());
        Map<String, String> replace1 = new HashMap<>();
        replace1.put("login_user", "admin");
        String replace = link.replace("当前登录人为:${login_user}, 密码为:${password:xxxx},但是登录人${login_user}最好不要修改密码", replace1);
        System.out.println(replace);
        replace1.put("password", "123");
        String replace2 = link.replace("当前登录人为:${login_user}, 密码为:${password:xxxx},但是登录人${login_user}最好不要修改密码", replace1);
        System.out.println(replace2);

    }
}
