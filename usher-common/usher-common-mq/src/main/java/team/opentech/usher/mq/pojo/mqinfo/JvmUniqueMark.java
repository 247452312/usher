package team.opentech.usher.mq.pojo.mqinfo;

import team.opentech.usher.mq.content.RabbitMqContent;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * jvm唯一标示
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 09时11分
 */
@Component
public class JvmUniqueMark implements Serializable {

    private static JvmUniqueMark jvmUniqueMark;

    /**
     * 服务名称
     */
    @Value("${rpc.application.name}")
    private String serviceName;

    /**
     * 服务所在ip -> 区分集群信息
     */
    private String ip;

    /**
     * 时间戳 --> 区分同一服务的另一个时间戳
     */
    private Long time;

    private JvmUniqueMark() {
        this.ip = RabbitMqContent.IP;
        this.time = RabbitMqContent.START_TIME;
    }

    public static JvmUniqueMark getInstance() {
        if (null == jvmUniqueMark) {
            synchronized (JvmUniqueMark.class) {
                if (null == jvmUniqueMark) {
                    jvmUniqueMark = new JvmUniqueMark();
                }
            }
        }
        return jvmUniqueMark;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
