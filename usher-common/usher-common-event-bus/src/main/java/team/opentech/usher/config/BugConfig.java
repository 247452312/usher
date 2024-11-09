package team.opentech.usher.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.opentech.usher.bus.Bus;
import team.opentech.usher.bus.BusInterface;
import team.opentech.usher.mq.util.MqUtil;
import team.opentech.usher.protocol.register.base.Register;

import java.util.List;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月19日 10时26分
 */
@Configuration
public class BugConfig {


    @Bean
    public BusInterface eventBus(List<Register> registers) throws MQClientException {
        return MqUtil.addConsumer(BusInterface.class, new Bus(registers));
    }
}
