package top.uhyils.usher.config;

import java.util.List;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.uhyils.usher.bus.Bus;
import top.uhyils.usher.bus.BusInterface;
import top.uhyils.usher.mq.util.MqUtil;
import top.uhyils.usher.protocol.register.base.Register;


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
