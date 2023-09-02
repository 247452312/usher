package team.opentech.usher.mq.filter;

import team.opentech.usher.mq.consumer.MQConsumer;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 19时12分
 */
public interface MQConsumerFilter {

    /**
     * 对consumer做一系列的动作然后返回
     *
     * @param consumer
     *
     * @return
     */
    <T extends MQConsumer> T filter(T consumer);


}
