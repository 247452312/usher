package top.uhyils.usher.pojo.link;

import java.util.Map;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 14时17分
 */
public class HttpLinkInfo implements LinkInfo {


    /**
     * POST GET等
     */
    private String type;

    /**
     * header
     */
    private Map<String, String> header;

    /**
     * 参数
     */
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }
}
