package top.uhyils.usher.rpc.spring;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import top.uhyils.usher.rpc.config.RpcConfig;
import top.uhyils.usher.rpc.config.RpcConfigFactory;
import top.uhyils.usher.util.LogUtil;

/**
 * 初始化配置类(单独写一个是为了区分先后, 先加载配置
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月17日 11时12分
 */
@Configuration
@Import(RpcAutoConfiguration.class)
@AutoConfigureBefore(RpcAutoConfiguration.class)
public class RpcConfigAutoInitConfiguration {


    /**
     * 初始化配置类
     *
     * @return
     */
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "rpc")
    public RpcConfig rpcConfig() {
        LogUtil.info("rpcConfig init!!");
        RpcConfig rpcConfig = new RpcConfig();
        RpcConfigFactory.setRpcConfig(rpcConfig);
        return rpcConfig;
    }
}
