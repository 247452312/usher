package top.uhyils.usher.pojo.cqe;

import java.io.Serializable;
import java.util.Map;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月05日 15时15分
 */
public class HttpRequestInfo implements Serializable {

    /**
     * url
     */
    private String url;

    /**
     * 使用https
     */
    private Boolean useHttps;

    /**
     * 请求头
     */
    private Map<String, String> heads;

    /**
     * 请求参数
     */
    private Map<String, Object> params;

    /**
     * 类型
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getUseHttps() {
        return useHttps;
    }

    public void setUseHttps(Boolean useHttps) {
        this.useHttps = useHttps;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeads() {
        return heads;
    }

    public void setHeads(Map<String, String> heads) {
        this.heads = heads;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
