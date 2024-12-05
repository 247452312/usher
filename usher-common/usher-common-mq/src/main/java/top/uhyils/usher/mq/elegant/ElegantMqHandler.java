package top.uhyils.usher.mq.elegant;


import java.util.HashMap;
import java.util.Map;
import org.apache.rocketmq.client.exception.MQClientException;
import top.uhyils.usher.elegant.AbstractElegantHandler;
import top.uhyils.usher.mq.core.BaseMqConsumer;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月25日 17时07分
 */
public class ElegantMqHandler extends AbstractElegantHandler {


    /**
     * 保存consumer
     */
    private final Map<String, BaseMqConsumer> consumers = new HashMap<>();

    public void addConsumer(BaseMqConsumer consumer) {
        String join = String.join("||", consumer.tags());
        String name = consumer.topic() + " " + join;
        synchronized (consumers) {
            consumers.put(name, consumer);
            if (canPublish()) {
                consumer.resume();
            }
        }

    }

    @Override
    public void allowToPublish() {
        super.allowToPublish();
        synchronized (consumers) {
            for (BaseMqConsumer value : consumers.values()) {
                value.resume();
            }
        }
    }

    @Override
    public void notAllowToPublish() {
        super.notAllowToPublish();
        synchronized (consumers) {
            for (BaseMqConsumer value : consumers.values()) {
                value.suspend();
            }
        }
    }


    @Override
    public Boolean isOnline() {
        return Boolean.TRUE;
    }

    @Override
    public void close() {
        doShutdown();
    }

    @Override
    public String name() {
        return "MQ";
    }

    @Override
    protected void doShutdown() {
        synchronized (consumers) {
            for (BaseMqConsumer value : consumers.values()) {
                try {
                    value.shutdown();
                } catch (MQClientException e) {
                    LogUtil.error(this, e);
                }
            }
        }
    }
}
