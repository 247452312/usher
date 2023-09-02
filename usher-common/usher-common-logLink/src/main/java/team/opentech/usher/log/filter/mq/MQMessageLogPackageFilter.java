package team.opentech.usher.log.filter.mq;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import team.opentech.usher.context.MyTraceIdContext;
import team.opentech.usher.enums.LogTypeEnum;
import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.filter.MQMessageFilter;
import team.opentech.usher.pojo.other.RpcTraceInfo;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.SupplierWithException;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 19时19分
 */
@Component
public class MQMessageLogPackageFilter implements MQMessageFilter {

    @Override
    public MQMessage invoke(MQMessage message) {
        MqSendInfo build = MqSendInfo.build(message.body(), RpcTraceInfo.build(MyTraceIdContext.getThraceId(), MyTraceIdContext.getNextRpcIds()));
        String jsonString = JSON.toJSONString(build);
        SupplierWithException<MQMessage> direct = () -> new MQMessage(message.id(), message.topicName(), message.tag(), jsonString, message.header());
        try {
            return MyTraceIdContext.printLogInfo(LogTypeEnum.MQ, direct, new String[]{message.topicName(), message.tag()}, message.topicName(), message.tag());
        } catch (Throwable throwable) {
            Asserts.throwException(throwable);
            return null;
        }
    }
}
