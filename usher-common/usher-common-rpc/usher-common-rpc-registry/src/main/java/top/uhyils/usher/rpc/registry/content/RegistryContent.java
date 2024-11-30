package top.uhyils.usher.rpc.registry.content;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import top.uhyils.usher.rpc.registry.ConsumerRegistry;
import top.uhyils.usher.rpc.registry.ProviderRegistry;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月27日 08时48分
 */
public class RegistryContent {

    /**
     * rpc在注册时默认的组
     */
    public static final String DEFAULT_REGISTRY_GROUP_NAME = "myRpc";


    /**
     * 服务端注册中心
     */
    public static List<ProviderRegistry> PROVIDER_REGISTRY = new LinkedList<>();

    public static List<ConsumerRegistry> CONSUMER_REGISTRY = new ArrayList<>();
}
