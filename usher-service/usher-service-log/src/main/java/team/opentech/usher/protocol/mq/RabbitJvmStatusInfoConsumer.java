package team.opentech.usher.protocol.mq;

import org.springframework.context.ApplicationContext;
import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.UsherMq;
import team.opentech.usher.mq.UsherMqReceiveMethod;
import team.opentech.usher.mq.content.JVMContent;
import team.opentech.usher.mq.pojo.mqinfo.JvmStatusInfoCommand;
import team.opentech.usher.service.LogMonitorJvmStatusService;
import team.opentech.usher.util.LogUtil;

/**
 * 监听JVM状态消息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 11时33分
 */
@UsherMq(topic = JVMContent.JVM_STATUS_TAG, tags = {JVMContent.JVM_STATUS_TAG}, group = "监听JVM状态消息")
public class RabbitJvmStatusInfoConsumer {


    private LogMonitorJvmStatusService service;

    public RabbitJvmStatusInfoConsumer(ApplicationContext applicationContext) {
        service = applicationContext.getBean(LogMonitorJvmStatusService.class);
    }

    @UsherMqReceiveMethod
    public void handleDelivery(MQMessage message) {
        JvmStatusInfoCommand jvmStatusInfo = message.body(JvmStatusInfoCommand.class);
        LogUtil.info(this, "接收到JVM状态信息");
        service.receiveJvmStatusInfo(jvmStatusInfo);
    }
}
