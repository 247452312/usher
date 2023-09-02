package team.opentech.usher.mq.content;


import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import team.opentech.usher.util.IpUtil;

/**
 * 初始化定义的一些事情
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 07时07分
 */
public class JVMContent {


    public static final String JVM_TOPIC = "JVM_TOPIC";

    /**
     * JVM 开启通知管道
     */
    public static final String JVM_START_TAG = "jvm_start";


    /**
     * JVM状态信息
     */
    public static final String JVM_STATUS_TAG = "jvm_status";

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
        JVMContent.logServiceOnLine = logServiceOnLine;
    }

}
