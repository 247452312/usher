package top.uhyils.usher.protocol.mq;

import com.alibaba.fastjson.JSONObject;
import java.nio.charset.StandardCharsets;
import org.springframework.context.ApplicationContext;
import top.uhyils.usher.annotation.UsherMq;
import top.uhyils.usher.mq.content.RocketMqContent;
import top.uhyils.usher.mq.core.AbstractRocketMqConsumer;
import top.uhyils.usher.mq.core.RocketMqMessageResEnum;
import top.uhyils.usher.mq.pojo.mqinfo.JvmStartInfoCommand;
import top.uhyils.usher.service.LogMonitorService;
import top.uhyils.usher.util.LogUtil;

/**
 * 监听JVM启动消息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 11时33分
 */
@UsherMq(topic = RocketMqContent.JVM_START_TOPIC_NAME, tags = {RocketMqContent.JVM_START_TAG_NAME}, group = RocketMqContent.JVM_GROUP_NAME, isOrder = false)
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
    public RocketMqMessageResEnum doOnMessage(byte[] bytes) {
        try {
            String message = new String(bytes, StandardCharsets.UTF_8);
            JvmStartInfoCommand jvmStartInfo = JSONObject.parseObject(message, JvmStartInfoCommand.class);
            LogUtil.info(this, "接收到JVM启动信息");
            LogUtil.info(this, message);
            logMonitorService.receiveJvmStartInfo(jvmStartInfo);
            return RocketMqMessageResEnum.SUCCESS;
        } catch (Exception e) {
            LogUtil.error(this, e);
            throw e;
        }
    }
}
