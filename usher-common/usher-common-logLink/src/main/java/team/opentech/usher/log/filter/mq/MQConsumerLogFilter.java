package team.opentech.usher.log.filter.mq;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import org.springframework.stereotype.Component;
import team.opentech.usher.context.MyTraceIdContext;
import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.consumer.MQConsumer;
import team.opentech.usher.mq.filter.MQConsumerFilter;
import team.opentech.usher.pojo.other.RpcTraceInfo;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 19时16分
 */
@Component
public class MQConsumerLogFilter implements MQConsumerFilter {

    @Override
    public <T extends MQConsumer> T filter(T consumer) {
        return (T) Proxy.newProxyInstance(consumer.getClass().getClassLoader(), consumer.getClass().getInterfaces(), new MqInvocationHandler(consumer));
    }

    private static class MqInvocationHandler implements InvocationHandler {

        private static final String RECEIVE_METHOD_NAME = "receive";

        private final MQConsumer consumer;

        public MqInvocationHandler(MQConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (!Objects.equals(method.getName(), RECEIVE_METHOD_NAME)) {
                return method.invoke(consumer, args);
            }

            // 拆包装
            MQMessage arg = (MQMessage) args[0];
            MqSendInfo mqSendInfo = arg.body(MqSendInfo.class);
            RpcTraceInfo rpcInfo = mqSendInfo.getRpcInfo();

            String realInfo = mqSendInfo.getJson();
            MQMessage build = new MQMessage(arg.id(), arg.topicName(), arg.tag(), realInfo, arg.header());
            MyTraceIdContext.setThraceId(rpcInfo.getTraceId());
            MyTraceIdContext.setRpcId(rpcInfo.getRpcIds());
            try {
                return method.invoke(consumer, build);
            } finally {
                MyTraceIdContext.clean();
            }

        }
    }
}
