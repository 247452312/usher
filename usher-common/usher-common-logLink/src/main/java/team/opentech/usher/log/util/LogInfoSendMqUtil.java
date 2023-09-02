package team.opentech.usher.log.util;

import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.client.MQClient;
import team.opentech.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月29日 12时38分
 */
public class LogInfoSendMqUtil {

    public static final String LOG_TOPIC = "log_topic";

    public static final String LOG_TAG = "log_tag";


    /**
     * 发送日志信息
     *
     * @return
     */
    public static void sendLogInfo(String msg) {
        MQClient bean = SpringUtil.getBean(MQClient.class);
        bean.sendOriginal(new MQMessage(LOG_TOPIC, LOG_TAG, msg));
    }

    public static String getLogTopic() {
        return LOG_TOPIC;
    }

    public static String getLogTag() {
        return LOG_TAG;
    }

}
