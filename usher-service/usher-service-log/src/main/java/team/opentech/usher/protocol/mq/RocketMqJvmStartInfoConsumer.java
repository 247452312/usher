package team.opentech.usher.protocol.mq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationContext;
import team.opentech.usher.annotation.UsherMq;
import team.opentech.usher.mq.content.RocketMqContent;
import team.opentech.usher.mq.pojo.mqinfo.JvmStartInfoCommand;
import team.opentech.usher.protocol.mq.base.AbstractRocketMqConsumer;
import team.opentech.usher.protocol.mq.base.RocketMqMessageResEnum;
import team.opentech.usher.service.LogMonitorService;
import team.opentech.usher.util.LogUtil;

import java.nio.charset.StandardCharsets;

/**
 * 监听JVM启动消息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 11时33分
 */
@UsherMq(topic = RocketMqContent.JVM_START_TAG_NAME, tags = {RocketMqContent.JVM_START_TAG_NAME}, group = RocketMqContent.JVM_START_GROUP_NAME, isOrder = false)
public class RocketMqJvmStartInfoConsumer extends AbstractRocketMqConsumer {

    private LogMonitorService logMonitorService;

    /**
     * @param applicationContext
     */
    public RocketMqJvmStartInfoConsumer(ApplicationContext applicationContext) {
        super();
        logMonitorService = applicationContext.getBean(LogMonitorService.class);
    }


    @Override
    public RocketMqMessageResEnum onMessage(byte[] bytes) {
        String message = new String(bytes, StandardCharsets.UTF_8);
        JvmStartInfoCommand jvmStartInfo = JSONObject.parseObject(message, JvmStartInfoCommand.class);
        LogUtil.info(this, "接收到JVM启动信息");
        LogUtil.info(this, message);
        logMonitorService.receiveJvmStartInfo(jvmStartInfo);
        return RocketMqMessageResEnum.SUCCESS;
    }
}
