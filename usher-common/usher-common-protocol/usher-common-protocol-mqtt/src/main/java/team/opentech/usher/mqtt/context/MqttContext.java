package team.opentech.usher.mqtt.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.util.Asserts;

/**
 * mqtt上下文
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 09时42分
 */
public class MqttContext {

    /**
     * client连接缓存
     */
    private static volatile Map<String, MqttNettyHandler> CLIENT_IDENTIFIER_MAP = new ConcurrentHashMap<>();


    public static void add(String clientIdentifier, MqttNettyHandler nettyHandler) {
        Asserts.assertTrue(!CLIENT_IDENTIFIER_MAP.containsKey(clientIdentifier), "当前客户端id已存在");
        CLIENT_IDENTIFIER_MAP.put(clientIdentifier, nettyHandler);
    }

    public static void remove(String clientIdentifier) {
        CLIENT_IDENTIFIER_MAP.remove(clientIdentifier);
    }

    public static MqttNettyHandler findNettyHandler(String clientIdentifier) {
        return CLIENT_IDENTIFIER_MAP.get(clientIdentifier);
    }
}
