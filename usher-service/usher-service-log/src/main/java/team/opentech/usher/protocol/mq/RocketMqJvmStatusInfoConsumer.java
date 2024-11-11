package team.opentech.usher.protocol.mq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationContext;
import team.opentech.usher.annotation.UsherMq;
import team.opentech.usher.mq.content.RocketMqContent;
import team.opentech.usher.mq.pojo.mqinfo.JvmStatusInfoCommand;
import team.opentech.usher.protocol.mq.base.AbstractRocketMqConsumer;
import team.opentech.usher.protocol.mq.base.RocketMqMessageResEnum;
import team.opentech.usher.service.LogMonitorJvmStatusService;
import team.opentech.usher.util.LogUtil;

import java.nio.charset.StandardCharsets;

/**
 * 监听JVM状态消息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 11时33分
 */
@UsherMq(topic = RocketMqContent.JVM_TOPIC_NAME, tags = {RocketMqContent.JVM_STATUS_TAG_NAME}, group = RocketMqContent.JVM_GROUP_NAME, isOrder = true)
public class RocketMqJvmStatusInfoConsumer extends AbstractRocketMqConsumer {


    private LogMonitorJvmStatusService service;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param applicationContext
     */
    public RocketMqJvmStatusInfoConsumer(ApplicationContext applicationContext) {
        service = applicationContext.getBean(LogMonitorJvmStatusService.class);
    }


    @Override
    public RocketMqMessageResEnum onMessage(byte[] bytes) {
        String message = new String(bytes, StandardCharsets.UTF_8);
        LogUtil.info(this, "接收到JVM状态信息");
        LogUtil.info(this, message);
        JvmStatusInfoCommand jvmStatusInfo = JSONObject.parseObject(message, JvmStatusInfoCommand.class);
        service.receiveJvmStatusInfo(jvmStatusInfo);
        return RocketMqMessageResEnum.SUCCESS;
    }
}
