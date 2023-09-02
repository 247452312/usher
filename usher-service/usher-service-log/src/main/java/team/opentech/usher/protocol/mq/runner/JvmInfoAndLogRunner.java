package team.opentech.usher.protocol.mq.runner;

import javax.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import team.opentech.usher.log.util.LogInfoSendMqUtil;
import team.opentech.usher.mq.client.MQClient;
import team.opentech.usher.protocol.mq.RabbitLogInfoConsumer;

/**
 * 消费者们
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 11时27分
 */
@Component
public class JvmInfoAndLogRunner implements ApplicationRunner {

    @Resource
    private MQClient mqClient;

    @Override
    public void run(ApplicationArguments args) {
        /* 日志信息的(注,此queue流量巨大) */
        mqClient.addNoFilterConsumer(LogInfoSendMqUtil.getLogTopic(), RabbitLogInfoConsumer::new);
    }
}
