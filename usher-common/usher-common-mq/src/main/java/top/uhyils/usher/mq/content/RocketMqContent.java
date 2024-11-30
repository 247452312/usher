package top.uhyils.usher.mq.content;


import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import top.uhyils.usher.util.IpUtil;

/**
 * 初始化定义的一些事情
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 07时07分
 */
public class RocketMqContent {

    /**
     * exchange 路由名称
     */
    public static final String JVM_START_TOPIC_NAME = "JVM_START_LOG";

    /**
     * JVM 开启通知管道
     */
    public static final String JVM_START_TAG_NAME = "jvm_start";

    /**
     * exchange 路由名称
     */
    public static final String JVM_STATUS_TOPIC_NAME = "JVM_STATUS_LOG";
    /**
     * JVM状态信息
     */
    public static final String JVM_STATUS_TAG_NAME = "jvm_status";

    public static final String JVM_GROUP_NAME = "JVM_GROUP";



    /**
     * 数据库中默认假想超时时间 设置假想结束时间=JVM上次发送状态时间+ OUT_TIME*比例系数
     * 同时也是微服务发送监控间隔
     */
    public static final long OUT_TIME = 5L;

    /**
     * 数据库中默认假想超时时间比例系数 设置假想结束时间=JVM上次发送状态时间+ OUT_TIME*比例系数
     */
    public static final double OUT_TIME_PRO = 1.1;

    public static final String IP;

    public static final Long START_TIME;

    /**
     * log服务是否在线
     */
    private static volatile Boolean logServiceOnLine = Boolean.FALSE;

    static {
        IP = IpUtil.getIp();
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        START_TIME = bean.getStartTime();
    }


    public static void init() {

    }

    public static Boolean getLogServiceOnLine() {
        return logServiceOnLine;
    }

    public static void setLogServiceOnLine(Boolean logServiceOnLine) {
        RocketMqContent.logServiceOnLine = logServiceOnLine;
    }

}
