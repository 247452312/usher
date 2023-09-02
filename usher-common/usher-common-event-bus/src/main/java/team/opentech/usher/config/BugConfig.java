package team.opentech.usher.config;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.opentech.usher.bus.Bus;
import team.opentech.usher.bus.BusInterface;
import team.opentech.usher.mq.client.MQClient;
import team.opentech.usher.protocol.register.base.Register;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月19日 10时26分
 */
@Configuration
public class BugConfig {

    @Resource
    private MQClient mqClient;

    @Bean
    public BusInterface eventBus(List<Register> registers) {
        return mqClient.addConsumer(BusInterface.BUS_EVENT_TOPIC, () -> new Bus(registers));
    }
}
