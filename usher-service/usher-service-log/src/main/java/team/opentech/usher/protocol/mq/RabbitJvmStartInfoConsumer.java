package team.opentech.usher.protocol.mq;

import org.springframework.context.ApplicationContext;
import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.UsherMq;
import team.opentech.usher.mq.UsherMqReceiveMethod;
import team.opentech.usher.mq.content.JVMContent;
import team.opentech.usher.mq.pojo.mqinfo.JvmStartInfoCommand;
import team.opentech.usher.service.LogMonitorService;
import team.opentech.usher.util.LogUtil;

/**
 * 监听JVM启动消息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 11时33分
 */
@UsherMq(topic = JVMContent.JVM_START_TAG, tags = {JVMContent.JVM_START_TAG}, group = "监听JVM启动消息")
public class RabbitJvmStartInfoConsumer {

    private LogMonitorService logMonitorService;

    public RabbitJvmStartInfoConsumer(ApplicationContext applicationContext) {
        logMonitorService = applicationContext.getBean(LogMonitorService.class);
    }


    @UsherMqReceiveMethod
    public void handleDelivery(MQMessage message) {
        JvmStartInfoCommand jvmStartInfo = message.body(JvmStartInfoCommand.class);
        LogUtil.info(this, "接收到JVM启动信息");
        logMonitorService.receiveJvmStartInfo(jvmStartInfo);
    }
}
