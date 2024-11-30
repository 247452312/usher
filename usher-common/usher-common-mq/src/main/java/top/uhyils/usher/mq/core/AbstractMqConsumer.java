package top.uhyils.usher.mq.core;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import top.uhyils.usher.annotation.UsherMq;
import top.uhyils.usher.elegant.ElegantHandler;
import top.uhyils.usher.mq.elegant.ElegantMqHandler;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年03月14日 15时44分
 */
public abstract class AbstractMqConsumer implements BaseMqConsumer {


    protected MQPushConsumer mq;

    protected AbstractMqConsumer() {
        super();
    }

    @Override
    public String topic() {
        UsherMq annotation = this.getClass().getAnnotation(UsherMq.class);
        return annotation.topic();
    }

    @Override
    public List<String> tags() {
        UsherMq annotation = this.getClass().getAnnotation(UsherMq.class);
        return Arrays.stream(annotation.tags()).collect(Collectors.toList());
    }

    @Override
    public String group() {
        UsherMq annotation = this.getClass().getAnnotation(UsherMq.class);
        return annotation.group();
    }

    @Override
    public boolean isOrder() {
        UsherMq annotation = this.getClass().getAnnotation(UsherMq.class);
        return annotation.isOrder();
    }


    @Override
    public void setPushConsumer(MQPushConsumer mq) {
        this.mq = mq;
    }

    @Override
    public void start() throws MQClientException {
        Asserts.assertTrue(this.mq != null, "MQConsumer未加载,无法启动");
        this.mq.start();
    }

    @Override
    public void shutdown() throws MQClientException {
        Asserts.assertTrue(this.mq != null, "MQConsumer未加载,无法关闭");
        this.mq.shutdown();
    }

    @Override
    public void suspend() {
        Asserts.assertTrue(this.mq != null, "MQConsumer未加载,无法暂停");
        this.mq.suspend();

    }

    @Override
    public void resume() {
        Asserts.assertTrue(this.mq != null, "MQConsumer未加载,无法继续");
        this.mq.resume();
    }

    @Override
    public RocketMqMessageResEnum onMessage(byte[] message) {
        ElegantHandler bean = SpringUtil.getBean(ElegantMqHandler.class);
        if (!bean.isOnline()) {
            return RocketMqMessageResEnum.ERROR;
        }
        bean.newRequest();
        try {
            return doOnMessage(message);
        } finally {
            bean.requestOver();
        }
    }

    protected abstract RocketMqMessageResEnum doOnMessage(byte[] message);
}
