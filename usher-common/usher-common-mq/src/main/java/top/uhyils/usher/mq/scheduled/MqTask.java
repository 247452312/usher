package top.uhyils.usher.mq.scheduled;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.Collections;
import javax.annotation.PostConstruct;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.uhyils.usher.mq.content.RocketMqContent;
import top.uhyils.usher.mq.pojo.mqinfo.JvmStartInfoCommand;
import top.uhyils.usher.mq.pojo.mqinfo.JvmStatusInfoCommand;
import top.uhyils.usher.mq.pojo.mqinfo.JvmUniqueMark;
import top.uhyils.usher.mq.util.JvmUtil;
import top.uhyils.usher.mq.util.MqUtil;
import top.uhyils.usher.util.LogUtil;

/**
 * MQ发送者
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 07时38分
 */
@Component
@EnableScheduling
public class MqTask {

    /**
     * 这个微服务的唯一标示
     */
    @Autowired
    private volatile JvmUniqueMark jvmUniqueMark;

    /**
     * 从程序启动开始 每半小时执行一次
     *
     * @throws Exception MQ管道连接异常
     */
    @PostConstruct
    @Scheduled(cron = "0 */" + RocketMqContent.OUT_TIME + " * * * ?")
    public void sendInfo() throws Exception {
        // 发送监控前主动gc一次
        System.gc();
        // 如果start信息没有发送过,那么发送start信息(只有项目启动时发送start信息失败时重复发送)
        if (!RocketMqContent.getLogServiceOnLine()) {
            JvmStartInfoCommand jvmStartInfo = JvmUtil.getJvmStartInfo(jvmUniqueMark);
            MqUtil.sendConfirmMsg(RocketMqContent.JVM_START_TOPIC_NAME, Collections.singletonList(RocketMqContent.JVM_START_TAG_NAME), new SendCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    synchronized (this) {
                        LogUtil.info(this, "JVM启动消息处理成功(定时任务)");
                        // 设置interface可以开始干活了
                        RocketMqContent.setLogServiceOnLine(Boolean.TRUE);
                        // 设置为空 释放内存
                        JvmStartInfoCommand.setStatusInfos(new ArrayList<>());
                    }
                }

                @Override
                public void onException(Throwable e) {
                    LogUtil.error(this, e, "启动信息处理失败(定时任务)");
                }
            }, JSON.toJSONString(jvmStartInfo));
        } else {

            JvmStatusInfoCommand jvmStatusInfo = JvmUtil.getJvmStatusInfo(jvmUniqueMark);
            // 否则正常发送
            MqUtil.sendConfirmMsg(RocketMqContent.JVM_STATUS_TOPIC_NAME, Collections.singletonList(RocketMqContent.JVM_STATUS_TAG_NAME), new SendCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    LogUtil.info(this, "JVM状态消息处理成功");
                    // 成功了就开启interface发送
                    RocketMqContent.setLogServiceOnLine(Boolean.TRUE);
                }

                @Override
                public void onException(Throwable e) {
                    // 失败了就取消interface发送
                    RocketMqContent.setLogServiceOnLine(Boolean.FALSE);
                    LogUtil.warn(this, "JVM信息处理失败");
                }
            }, JSON.toJSONString(jvmStatusInfo));
        }
    }

    public JvmUniqueMark getJvmUniqueMark() {
        return jvmUniqueMark;
    }

}
