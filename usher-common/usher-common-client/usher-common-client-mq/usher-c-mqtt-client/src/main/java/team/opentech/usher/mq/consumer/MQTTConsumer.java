package team.opentech.usher.mq.consumer;

import team.opentech.usher.mq.MQContent;

/**
 * mqtt的Consumer
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 17时43分
 */
public interface MQTTConsumer extends MQConsumer {

    @Override
    default String[] tag() {
        return new String[]{MQContent.MQ_DEFAULT_NAME};
    }

    @Override
    default String groupName() {
        return MQContent.MQ_DEFAULT_NAME;
    }
}
