package top.uhyils.usher.mq.pojo.rocket;

import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.remoting.RPCHook;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.mq.core.BaseMqConsumer;

/**
 * rocketMq连接创建工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月18日 08时16分
 */
public class RocketMqFactory {

    private final RocketMqConfigInfo mqConfig;

    public RocketMqFactory(RocketMqConfigInfo mqConfig) {
        this.mqConfig = mqConfig;
    }


    @NotNull
    public void initConsumer(BaseMqConsumer consumer) throws MQClientException {
        boolean isEnableAcl = !StringUtils.isEmpty(mqConfig.getAccessKey()) && !StringUtils.isEmpty(mqConfig.getSecretKey());
        RPCHook rpcHook = null;
        if (isEnableAcl) {
            rpcHook = new AclClientRPCHook(new SessionCredentials(mqConfig.getAccessKey(), mqConfig.getSecretKey()));
        }
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(consumer.group(), rpcHook);
        defaultMQPushConsumer.setNamesrvAddr(mqConfig.getNamesrvAddr());
        String join = String.join("||", consumer.tags());
        defaultMQPushConsumer.subscribe(consumer.topic(), join);
        consumer.setPushConsumer(defaultMQPushConsumer);
        consumer.registerMessageListener();
        consumer.suspend();
        consumer.start();
    }

    @NotNull
    public MQProducer getProducer() {
        AclClientRPCHook rpcHook = null;
        if (mqConfig.isACLEnabled()) {
            rpcHook = new AclClientRPCHook(new SessionCredentials(mqConfig.getAccessKey(), mqConfig.getSecretKey()));
        }

        DefaultMQProducer producer = new DefaultMQProducer(rpcHook);
        producer.setUseTLS(mqConfig.isUseTLS());
        producer.setInstanceName(mqConfig.getProducerName());
        producer.setProducerGroup(mqConfig.getProducerName());
        producer.setNamesrvAddr(mqConfig.getNamesrvAddr());
        try {
            producer.start();
        } catch (Exception e) {
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
        return producer;
    }

    @NotNull
    public MQProducer getTransactionProducer(TransactionListener listener) {
        AclClientRPCHook rpcHook = null;
        if (mqConfig.isACLEnabled()) {
            rpcHook = new AclClientRPCHook(new SessionCredentials(mqConfig.getAccessKey(), mqConfig.getSecretKey()));
        }

        // no transaction message
        TransactionMQProducer producer = new TransactionMQProducer(mqConfig.getProducerName(), rpcHook);
        producer.setUseTLS(mqConfig.isUseTLS());
        producer.setInstanceName(mqConfig.getProducerName());
        producer.setNamesrvAddr(mqConfig.getNamesrvAddr());
        producer.setTransactionListener(listener);
        try {
            producer.start();
        } catch (Exception e) {
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
        return producer;
    }

}
