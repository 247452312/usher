package team.opentech.usher.mq.util;

import com.alibaba.fastjson.JSON;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import team.opentech.usher.context.MyTraceIdContext;
import team.opentech.usher.enums.LogTypeEnum;
import team.opentech.usher.mq.pojo.rocket.RocketMqFactory;
import team.opentech.usher.pojo.other.RpcTraceInfo;
import team.opentech.usher.protocol.mq.base.BaseMqConsumer;
import team.opentech.usher.util.LogUtil;
import team.opentech.usher.util.SpringUtil;
import team.opentech.usher.util.SupplierWithException;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月22日 14时11分
 */
public class MqUtil {


    /**
     * 保存consumer
     */
    private static final Map<String, BaseMqConsumer> consumers = new HashMap<>();


    private MqUtil() {
    }

    /**
     * 添加消费者
     */
    public static BaseMqConsumer addConsumer(BaseMqConsumer consumer) throws MQClientException {
        RocketMqFactory factory = SpringUtil.getBean(RocketMqFactory.class);
        factory.initConsumer(consumer);
        final BaseMqConsumer proxyConsumer = (BaseMqConsumer) Proxy.newProxyInstance(consumer.getClass().getClassLoader(), new Class[]{BaseMqConsumer.class}, new MqInvocationHandler(consumer));
        String join = String.join("||", proxyConsumer.tags());
        String name = proxyConsumer.topic() + " " + join;
        consumers.put(name, proxyConsumer);
        return proxyConsumer;
    }

    /**
     * 添加消费者
     *
     * @param consumer 消费者创建逻辑
     */
    public static <T extends BaseMqConsumer> T addConsumer(Class<T> interfaceClass, BaseMqConsumer consumer) throws MQClientException {
        RocketMqFactory factory = SpringUtil.getBean(RocketMqFactory.class);
        factory.initConsumer(consumer);
        final T proxyConsumer = (T) Proxy.newProxyInstance(consumer.getClass().getClassLoader(), new Class[]{interfaceClass}, new MqInvocationHandler(consumer));
        String join = String.join("||", proxyConsumer.tags());
        String name = proxyConsumer.topic() + " " + join;
        consumers.put(name, proxyConsumer);
        return proxyConsumer;
    }

    /**
     * 添加消费者
     *
     * @param consumer 消费者创建逻辑
     */
    public static void addNoLogConsumer(BaseMqConsumer consumer) throws MQClientException {
        RocketMqFactory factory = SpringUtil.getBean(RocketMqFactory.class);
        factory.initConsumer(consumer);
        String join = String.join("||", consumer.tags());
        String name = consumer.topic() + " " + join;
        consumers.put(name, consumer);
    }

    /**
     * 推送信息到mq
     *
     * @param topic 队列名称
     * @param tags  tag
     * @param msg   发送的信息的byte
     *
     * @return
     */
    public static void sendMsg(String topic, List<String> tags, String msg) {
        MqSendInfo build = MqSendInfo.build(msg, RpcTraceInfo.build(MyTraceIdContext.getThraceId(), MyTraceIdContext.getNextTraceIds()));
        byte[] bytes = JSON.toJSONString(build).getBytes(StandardCharsets.UTF_8);
        sendMsg(topic, tags, bytes);
    }

    /**
     * 推送信息到mq
     *
     * @param topic 队列名称
     * @param tag   tag
     * @param msg   发送的信息的byte
     *
     * @return
     */
    public static void sendMsg(String topic, String tag, String msg) {
        MqSendInfo build = MqSendInfo.build(msg, RpcTraceInfo.build(MyTraceIdContext.getThraceId(), MyTraceIdContext.getNextTraceIds()));
        byte[] bytes = JSON.toJSONString(build).getBytes(StandardCharsets.UTF_8);
        sendMsg(topic, Collections.singletonList(tag), bytes);
    }

    /**
     * 推送信息到mq 需要监听回应
     *
     * @param topic    队列名称
     * @param tags     tags
     * @param callback 回应监听
     * @param msg      发送的信息
     *
     * @return
     */
    public static void sendConfirmMsg(String topic, List<String> tags, SendCallback callback, String msg) {
        try {
            String join = String.join("||", tags);
            MyTraceIdContext.printLogInfo(LogTypeEnum.MQ, () -> {
                MqSendInfo build = MqSendInfo.build(msg, RpcTraceInfo.build(MyTraceIdContext.getThraceId(), MyTraceIdContext.getNextTraceIds()));
                byte[] bytes = JSON.toJSONString(build).getBytes(StandardCharsets.UTF_8);
                try {
                    sendConfirmMsg(topic, tags, callback, bytes);
                } catch (Exception e) {
                    LogUtil.error(e);
                }
                return null;
            }, new String[]{topic, join}, topic, join);
        } catch (Throwable throwable) {
            LogUtil.error(throwable);
        }

    }

    /**
     * 推送信息到mq
     *
     * @param topic        路由名称
     * @param tags         队列名称
     * @param sendCallback 回应监听
     * @param obj          发送的信息
     *
     * @return
     */
    public static void sendConfirmMsg(String topic, List<String> tags, SendCallback sendCallback, Object obj) {
        String msg = JSON.toJSONString(obj);
        try {
            String join = String.join("||", tags);
            MyTraceIdContext.printLogInfo(LogTypeEnum.MQ, () -> {
                MqSendInfo build = MqSendInfo.build(msg, RpcTraceInfo.build(MyTraceIdContext.getThraceId(), MyTraceIdContext.getNextTraceIds()));
                byte[] bytes = JSON.toJSONString(build).getBytes(StandardCharsets.UTF_8);
                try {
                    sendConfirmMsg(topic, tags, sendCallback, bytes);
                } catch (Exception e) {
                    LogUtil.error(e);
                }
                return null;
            }, new String[]{topic, join}, topic, join);
        } catch (Throwable throwable) {
            LogUtil.error(throwable);
        }

    }

    /**
     * 推送信息到mq
     *
     * @param exchange 路由名称
     * @param queue    队列名称
     * @param msg      发送的信息的byte
     *
     * @return
     */
    protected static void sendMsgNoLog(String exchange, List<String> queue, String msg) {
        doSendMsg(exchange, queue, JSON.toJSONString(msg).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 推送信息到mq
     *
     * @param topic 队列名称
     * @param tags  tag名称
     * @param bytes 发送的信息的byte
     *
     * @return
     */
    private static void sendMsg(String topic, List<String> tags, byte[] bytes) {
        SupplierWithException<?> direct = () -> {
            doSendMsg(topic, tags, bytes);
            return null;
        };
        try {
            String tagsStr = String.join("||", tags);
            MyTraceIdContext.printLogInfo(LogTypeEnum.MQ, direct, new String[]{topic, tagsStr}, topic, tagsStr);
        } catch (Throwable throwable) {
            LogUtil.error(MqUtil.class, throwable);
        }
    }

    /**
     * 发送信息
     *
     * @param topic
     * @param tags
     * @param bytes
     */
    private static void doSendMsg(String topic, List<String> tags, byte[] bytes) {
        MQProducer producer = SpringUtil.getBean(MQProducer.class);
        try {
            producer.send(new Message(topic, String.join("||", tags), bytes));
        } catch (MQBrokerException | RemotingException | InterruptedException | MQClientException e) {
            LogUtil.error(e);
        }
    }

    /**
     * 推送信息到mq
     *
     * @param topic 队列名称
     * @param tags  tags
     * @param bytes 发送的信息的byte
     *
     * @return
     */
    private static void sendConfirmMsg(String topic, List<String> tags, SendCallback sendCallback, byte[] bytes) {
        String join = String.join("||", tags);

        SupplierWithException<?> direct = () -> {
            MQProducer producer = SpringUtil.getBean(MQProducer.class);
            producer.send(new Message(topic, join, bytes), sendCallback);
            return null;
        };
        try {
            MyTraceIdContext.printLogInfo(LogTypeEnum.MQ, direct, new String[]{topic, join}, topic, join);
        } catch (Throwable throwable) {
            LogUtil.error(throwable);
        }
    }

    static class MqQueueInfo {

        /**
         * 路由
         */
        private String topic;

        /**
         * 队列
         */
        private List<String> tags;

        public MqQueueInfo(String topic, List<String> tags) {
            this.topic = topic;
            this.tags = tags;
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTopic(), getTags());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MqQueueInfo that = (MqQueueInfo) o;
            return Objects.equals(getTopic(), that.getTopic()) && Objects.equals(getTags(), that.getTags());
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }

    private static class MqSendInfo {

        private String json;

        private RpcTraceInfo rpcInfo;

        public static MqSendInfo build(String json, RpcTraceInfo rpcInfo) {
            MqSendInfo build = new MqSendInfo();
            build.json = json;
            build.rpcInfo = rpcInfo;
            return build;
        }

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public RpcTraceInfo getRpcInfo() {
            return rpcInfo;
        }

        public void setRpcInfo(RpcTraceInfo rpcInfo) {
            this.rpcInfo = rpcInfo;
        }
    }

    private static class MqInvocationHandler implements InvocationHandler {

        private static final String RECEIVE_METHOD_NAME = "onMessage";

        private final BaseMqConsumer consumer;

        public MqInvocationHandler(BaseMqConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals(RECEIVE_METHOD_NAME)) {
                String json = (String) args[0];
                MqSendInfo mqSendInfo = JSON.parseObject(json, MqSendInfo.class);
                RpcTraceInfo rpcInfo = mqSendInfo.getRpcInfo();

                MyTraceIdContext.setThraceId(rpcInfo.getTraceId());
                MyTraceIdContext.setTraceId(rpcInfo.getRpcIds());
                try {
                    return method.invoke(consumer, json);
                } finally {
                    MyTraceIdContext.clean();
                }
            } else {
                return method.invoke(consumer, args);
            }
        }
    }
}
