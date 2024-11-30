package top.uhyils.usher.mq.pojo.rocket;

import static org.apache.rocketmq.client.ClientConfig.SEND_MESSAGE_WITH_VIP_CHANNEL_PROPERTY;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.MixAll;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月09日 13时02分
 */
@Configuration
@ConfigurationProperties(prefix = "rocketmq.config")
public class RocketMqConfigInfo {

    private volatile String namesrvAddr = System.getProperty(MixAll.NAMESRV_ADDR_PROPERTY, System.getenv(MixAll.NAMESRV_ADDR_ENV));

    private volatile String proxyAddr;

    private volatile String isVIPChannel = System.getProperty(SEND_MESSAGE_WITH_VIP_CHANNEL_PROPERTY, "true");


    private String dataPath = "/tmp/rocketmq-console/data";

    private boolean enableDashBoardCollect;

    private boolean loginRequired = false;

    private String accessKey;

    private String secretKey;

    private boolean useTLS = false;

    private Long timeoutMillis;

    private List<String> namesrvAddrs = new ArrayList<>();

    private List<String> proxyAddrs = new ArrayList<>();

    /**
     * 消息发布者名称
     */
    private String producerName;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        if (StringUtils.isNotBlank(namesrvAddr)) {
            this.namesrvAddr = namesrvAddr;
            System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY, namesrvAddr);
        }
    }

    public List<String> getNamesrvAddrs() {
        return namesrvAddrs;
    }

    public void setNamesrvAddrs(List<String> namesrvAddrs) {
        this.namesrvAddrs = namesrvAddrs;
        if (CollectionUtils.isNotEmpty(namesrvAddrs)) {
            this.setNamesrvAddr(namesrvAddrs.get(0));
        }
    }

    public List<String> getProxyAddrs() {
        return this.proxyAddrs;
    }

    public void setProxyAddrs(List<String> proxyAddrs) {
        this.proxyAddrs = proxyAddrs;
        if (CollectionUtils.isNotEmpty(proxyAddrs)) {
            this.setProxyAddr(proxyAddrs.get(0));
        }
    }

    public String getProxyAddr() {
        return proxyAddr;
    }

    public void setProxyAddr(String proxyAddr) {
        this.proxyAddr = proxyAddr;
    }

    public boolean isACLEnabled() {
        return !(StringUtils.isAnyBlank(this.accessKey, this.secretKey) ||
                 StringUtils.isAnyEmpty(this.accessKey, this.secretKey));
    }

    public String getRocketMqDashboardDataPath() {
        return dataPath;
    }

    public String getDashboardCollectData() {
        return dataPath + File.separator + "dashboard";
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getIsVIPChannel() {
        return isVIPChannel;
    }

    public void setIsVIPChannel(String isVIPChannel) {
        if (StringUtils.isNotBlank(isVIPChannel)) {
            this.isVIPChannel = isVIPChannel;
            System.setProperty(SEND_MESSAGE_WITH_VIP_CHANNEL_PROPERTY, isVIPChannel);
        }
    }

    public boolean isEnableDashBoardCollect() {
        return enableDashBoardCollect;
    }

    public void setEnableDashBoardCollect(String enableDashBoardCollect) {
        this.enableDashBoardCollect = Boolean.valueOf(enableDashBoardCollect);
    }

    public boolean isLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(boolean loginRequired) {
        this.loginRequired = loginRequired;
    }

    public boolean isUseTLS() {
        return useTLS;
    }

    public void setUseTLS(boolean useTLS) {
        this.useTLS = useTLS;
    }

    public Long getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(Long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    // Error Page process logic, move to a central configure later
    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return new MyErrorPageRegistrar();
    }

    public String getProducerName() {
        return this.producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

        @Override
        public void registerErrorPages(ErrorPageRegistry registry) {
            registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
        }

    }
}
