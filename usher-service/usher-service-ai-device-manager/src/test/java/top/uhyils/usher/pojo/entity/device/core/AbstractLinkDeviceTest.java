package top.uhyils.usher.pojo.entity.device.core;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.pojo.entity.device.demo.SingleDevice;
import top.uhyils.usher.pojo.entity.link.http.HttpLink;
import top.uhyils.usher.pojo.link.HttpLinkInfo;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 15时40分
 */
class AbstractLinkDeviceTest {

    @Test
    void requestSync() {
        HttpLinkInfo linkInfo = new HttpLinkInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceName", "UserProvider");
        params.put("methodName", "login");
        Map<String, Object> args = new HashMap<>();
        args.put("username", "admin");
        args.put("password", "123456");

        params.put("args", args);
        linkInfo.setParams(params);
        linkInfo.setType("POST");
        Map<String, String> header = new HashMap<>();
        header.put("content-type", "application/json");
        linkInfo.setHeader(header);

        AbstractLinkDevice device = SingleDevice.buildUniqueSingleDevice(new HashMap<>(), "123", new HttpLink("http://localhost:8001/action", linkInfo));
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        String s = device.requestSync(JSON.toJSONString(map));
        System.out.println(s);
    }
}
