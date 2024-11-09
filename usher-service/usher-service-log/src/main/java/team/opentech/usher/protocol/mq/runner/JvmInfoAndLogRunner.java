package team.opentech.usher.protocol.mq.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import team.opentech.usher.mq.util.MqUtil;
import team.opentech.usher.protocol.mq.RocketMqJvmStartInfoConsumer;
import team.opentech.usher.protocol.mq.RocketMqJvmStatusInfoConsumer;
import team.opentech.usher.protocol.mq.RocketMqLogInfoConsumer;

/**
 * 消费者们
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 11时27分
 */
@Component
public class JvmInfoAndLogRunner implements ApplicationRunner {


    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /* 第1个是启动JVM_START信息的 */
        MqUtil.addConsumer(new RocketMqJvmStartInfoConsumer(applicationContext));

        /* 第2个是启动JVM_STATUS信息的 */
        MqUtil.addConsumer(new RocketMqJvmStatusInfoConsumer(applicationContext));

        /* 第3个是日志信息的(注,此queue流量巨大) */
        MqUtil.addNoLogConsumer(new RocketMqLogInfoConsumer(applicationContext));
    }
}
