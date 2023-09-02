package team.opentech.usher.mq.scheduled;

import com.alibaba.fastjson.JSON;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.client.MQCallBack;
import team.opentech.usher.mq.client.MQClient;
import team.opentech.usher.mq.content.JVMContent;
import team.opentech.usher.mq.pojo.BackInfo;
import team.opentech.usher.mq.pojo.mqinfo.JvmStartInfoCommand;
import team.opentech.usher.mq.pojo.mqinfo.JvmStatusInfoCommand;
import team.opentech.usher.mq.pojo.mqinfo.JvmUniqueMark;
import team.opentech.usher.mq.util.JvmUtil;
import team.opentech.usher.util.LogUtil;

/**
 * MQ发送者
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 07时38分
 */
@Component
@EnableScheduling
public class JVMMqTask {

    /**
     * 这个微服务的唯一标示
     */
    @Resource
    private volatile JvmUniqueMark jvmUniqueMark;

    @Resource
    private MQClient mqClient;

    /**
     * 从程序启动开始 每半小时执行一次
     *
     * @throws Exception MQ管道连接异常
     */
    @PostConstruct
    @Scheduled(cron = "0 */" + JVMContent.OUT_TIME + " * * * ?")
    public void sendInfo() throws Exception {
        // 发送监控前主动gc一次
        System.gc();
        // 如果start信息没有发送过,那么发送start信息(只有项目启动时发送start信息失败时重复发送)
        if (!JVMContent.getLogServiceOnLine()) {
            JvmStartInfoCommand jvmStartInfo = JvmUtil.getJvmStartInfo(jvmUniqueMark);
            mqClient.send(new MQMessage(JVMContent.JVM_TOPIC, JVMContent.JVM_START_TAG, JSON.toJSONString(jvmStartInfo)), new MQCallBack() {

                @Override
                public void invoke(BackInfo backInfo) {
                    if (Objects.equals(backInfo.getSuccess(), Boolean.TRUE)) {
                        synchronized (this) {
                            LogUtil.info(this, "JVM启动消息处理成功(定时任务)");
                            // 设置interface可以开始干活了
                            JVMContent.setLogServiceOnLine(Boolean.TRUE);
                            // 设置为空 释放内存
                            JvmStartInfoCommand.setStatusInfos(null);
                        }
                    } else {
                        LogUtil.warn("启动信息处理失败(定时任务),失败信息:{}", backInfo.getMsg());
                    }
                }
            });
        } else {

            JvmStatusInfoCommand jvmStatusInfo = JvmUtil.getJvmStatusInfo(jvmUniqueMark);
            // 否则正常发送
            mqClient.send(new MQMessage(JVMContent.JVM_TOPIC, JVMContent.JVM_STATUS_TAG, JSON.toJSONString(jvmStatusInfo)), new MQCallBack() {

                @Override
                public void invoke(BackInfo backInfo) {
                    if (Objects.equals(backInfo.getSuccess(), Boolean.TRUE)) {
                        LogUtil.info(this, "JVM状态消息处理成功");
                        // 成功了就开启interface发送
                        JVMContent.setLogServiceOnLine(Boolean.TRUE);
                    } else {
                        // 失败了就取消interface发送
                        JVMContent.setLogServiceOnLine(Boolean.FALSE);
                        LogUtil.warn(this, "JVM信息处理失败");
                    }
                }
            });
        }
    }

    public JvmUniqueMark getJvmUniqueMark() {
        return jvmUniqueMark;
    }

}
