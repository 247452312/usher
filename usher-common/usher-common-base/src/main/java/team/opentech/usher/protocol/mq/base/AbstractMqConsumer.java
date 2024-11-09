package team.opentech.usher.protocol.mq.base;

import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import team.opentech.usher.annotation.UsherMq;
import team.opentech.usher.util.Asserts;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
}
