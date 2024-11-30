package top.uhyils.usher.mq.pojo.mqinfo;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.uhyils.usher.mq.content.RocketMqContent;

/**
 * jvm唯一标示
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 09时11分
 */
@Component
public class JvmUniqueMark implements Serializable {

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
        this.ip = RocketMqContent.IP;
        this.time = RocketMqContent.START_TIME;
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
