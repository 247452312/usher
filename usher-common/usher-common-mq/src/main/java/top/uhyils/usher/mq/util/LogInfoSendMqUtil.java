package top.uhyils.usher.mq.util;

import java.util.Collections;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月29日 12时38分
 */
public class LogInfoSendMqUtil {

    public static final String TOPIC_NAME = "log_topic";

    public static final String TAG_NAME = "log_tag";
    public static final String GROUP_NAME = "log_group";


    /**
     * 发送日志信息
     *
     * @return
     */
    public static void sendLogInfo(String msg) {
        MqUtil.sendMsgNoLog(TOPIC_NAME, Collections.singletonList(TAG_NAME), msg);
    }

    public static String getTopicName() {
        return TOPIC_NAME;
    }

    public static String getTagName() {
        return TAG_NAME;
    }

}
