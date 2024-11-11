package team.opentech.usher.mq.config;

import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.opentech.usher.mq.pojo.rocket.RocketMqConfigInfo;
import team.opentech.usher.mq.pojo.rocket.RocketMqFactory;

/**
 * 初始化MqBean 配置文件中配置的东西
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 19时03分
 */
@Configuration
public class MqConfig {

    @Bean
    public RocketMqFactory getRocketMqFactory(RocketMqConfigInfo rocketMqConfigInfo) {
        return new RocketMqFactory(rocketMqConfigInfo);
    }

    @Bean
    public MQProducer makeMqProducer(RocketMqFactory mqFactory) {
        return mqFactory.getProducer();
    }

}
