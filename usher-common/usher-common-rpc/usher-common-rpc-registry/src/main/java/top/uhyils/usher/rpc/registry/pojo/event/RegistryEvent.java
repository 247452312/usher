package top.uhyils.usher.rpc.registry.pojo.event;


import java.util.List;
import java.util.Map;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;

/**
 * 注册中心发来的消息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年03月30日 16时49分
 */
public class RegistryEvent {

    /**
     * 对应的服务信息
     * key->集群名称 value 集群中所有服务器
     */
    private Map<String, List<NettyInitDto>> registryNettyInfoMap;

    public Map<String, List<NettyInitDto>> getRegistryNettyInfoMap() {
        return registryNettyInfoMap;
    }

    public void setRegistryNettyInfoMap(Map<String, List<NettyInitDto>> registryNettyInfoMap) {
        this.registryNettyInfoMap = registryNettyInfoMap;
    }
}
