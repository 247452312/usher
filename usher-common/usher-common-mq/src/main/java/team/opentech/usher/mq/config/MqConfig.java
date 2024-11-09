package team.opentech.usher.mq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import team.opentech.usher.mq.pojo.rocket.RocketMqConfig;
import team.opentech.usher.mq.pojo.rocket.RocketMqFactory;

/**
 * 初始化MqBean 配置文件中配置的东西
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 19时03分
 */
@Component
public class MqConfig {


    private final RocketMqConfig rocketMqConfig;

    public MqConfig(RocketMqConfig rocketMqConfig) {
        this.rocketMqConfig = rocketMqConfig;
    }


    @Bean
    public RocketMqFactory getRocketMqFactory() {
        return RocketMqFactory.getInstance(rocketMqConfig);
    }

}
