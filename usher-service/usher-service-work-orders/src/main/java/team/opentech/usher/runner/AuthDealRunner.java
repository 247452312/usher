package team.opentech.usher.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import team.opentech.usher.content.OrderContent;
import team.opentech.usher.handler.rabbit.OrderAutoDealConsumer;
import team.opentech.usher.mq.client.MQClient;

/**
 * 自动处理工单的地方
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月22日 17时44分
 */
@Component
public class AuthDealRunner implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MQClient mqClient;


    @Override
    public void run(ApplicationArguments args) {
        /* 启动接收工单自动节点信息 */
        mqClient.addConsumer(OrderContent.ORDER_TOPIC, () -> new OrderAutoDealConsumer(applicationContext));

    }
}
