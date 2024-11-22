package team.opentech.usher.protocol.mq.base;


import team.opentech.usher.protocol.mq.listener.UsherConcurrentlyListener;
import team.opentech.usher.protocol.mq.listener.UsherOrderlyListener;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月09日 11时14分
 */
public abstract class AbstractRocketMqConsumer extends AbstractMqConsumer {

    protected BaseMqConsumer proxyConsumer;

    /**
     * 注册rocketMq消费者
     */
    public void registerMessageListener() {
        if (isOrder()) {
            mq.registerMessageListener(new UsherOrderlyListener(proxyConsumer));
        } else {
            mq.registerMessageListener(new UsherConcurrentlyListener(proxyConsumer));
        }
    }

    @Override
    public void selfObserver(BaseMqConsumer observer) {
        this.proxyConsumer = observer;
    }
}
