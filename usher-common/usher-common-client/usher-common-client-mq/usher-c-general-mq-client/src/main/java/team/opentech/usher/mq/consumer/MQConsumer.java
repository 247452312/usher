package team.opentech.usher.mq.consumer;

import team.opentech.usher.mq.MQMessage;

/**
 * 消息监听
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 16时24分
 */
public interface MQConsumer {

    /**
     * 接收消息
     *
     * @param message
     */
    void receive(MQMessage message);

    /**
     * 监听的名称
     *
     * @return
     */
    String topic();

    /**
     * 监听的tag名称
     */
    String[] tag();

    /**
     * 客户端组名称
     *
     * @return
     */
    String groupName();

}
